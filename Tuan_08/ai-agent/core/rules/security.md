# Security (Phase MVP)

## Auth

Đề bài không yêu cầu authentication. `userId` hardcode = `"u1"` ở FE và truyền qua request.

Phase 2 (nếu mở rộng): JWT, lưu session vào Redis `session:{token}`.

## CORS

Mỗi PU phải có `CorsConfig`:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://192.168.137.41:3000",
                "http://localhost:3000"
            )
            .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
            .allowedHeaders("*");
    }
}
```

## Redis

- LAN nội bộ (192.168.137.x), không expose ra Internet.
- `protected-mode no` chỉ chấp nhận trong scope bài tập.
- **Không** lưu password, token, PII trong Redis.

## Input validation

- Bắt buộc `@Valid` cho mọi `@RequestBody`.
- `@NotBlank String userId`, `@Min(1) int quantity`.
- ProductId validate regex `^p\d{3}$` (phòng injection key Redis).
