# Space-Based Architecture Rules (BẮT BUỘC)

Đây là rule chấm điểm. Vi phạm = mất điểm "Đúng Space-Based" (3đ) + "Không phụ thuộc DB" (2.5đ).

## 1. Không có database truyền thống

- Pom.xml **không được** chứa `spring-boot-starter-data-jpa`, `spring-boot-starter-jdbc`, `mysql-connector-j`, `postgresql`, `h2`, `hibernate-core`.
- Không có `application.yml` cấu hình `spring.datasource.*`.
- Không có entity `@Entity`, `@Table`, `@Repository extends JpaRepository`.

## 2. Mọi state đi qua Data Grid

- Class duy nhất truy cập data: `*Repository` wrapper bọc `RedisTemplate`.
- Service inject Repository, **không** inject `RedisTemplate` trực tiếp (giữ controller/service không biết Redis).

```java
@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    public List<Product> findAll() {
        return redisTemplate.opsForHash()
            .values("products:all").stream()
            .map(v -> (Product) v).toList();
    }
}
```

## 3. Atomic operations cho stock

- Decrement stock **PHẢI** dùng `DECR` (`opsForValue().decrement(...)`), không phải `GET → SET`.
- Nếu `decrement` trả về < 0 → `increment` rollback ngay + trả 409.

```java
Long remaining = redisTemplate.opsForValue().decrement("stock:" + id, qty);
if (remaining != null && remaining < 0) {
    redisTemplate.opsForValue().increment("stock:" + id, qty);
    throw new OutOfStockException(id);
}
```

**Cấm**:
```java
// ❌ SAI: race condition
Long stock = redisTemplate.opsForValue().get("stock:" + id);
if (stock >= qty) {
    redisTemplate.opsForValue().set("stock:" + id, stock - qty);
}
```

## 4. Mỗi PU stateless

- Không có `@Component` lưu state in-memory ngoài cache local của Spring (Redis cache, không HashMap).
- PU phải clone-able: 2 instance cùng đọc/ghi Redis không gây sai data.
- Session: nếu cần (phase 2), lưu vào Redis với key `session:{sessionId}`.

## 5. Giao tiếp giữa PU

- ✅ Qua REST (HTTP).
- ✅ Qua Redis Data Grid (đọc cùng key).
- ❌ Không import package của PU khác.
- ❌ Không share Java class giữa các project (mỗi PU define DTO của riêng mình).
