# CLAUDE.md — Flash Sale SBA (Chương 7)

## 0. AI-Agent Context — Đọc trước khi làm bất cứ việc gì

**Đây là project bài tập nhóm Chương 7: Flash Sale theo Space-Based Architecture.** Mọi context, kiến trúc, rules, workflows đều ở folder `ai-agent/`.

### Bảng phân công (5 máy LAN 192.168.137.x)

| Service | IP:Port | Member |
|---|---|---|
| PU1 Product | 192.168.137.157:8081 | Huy |
| PU2 Cart | 192.168.137.184:8082 | Hiền |
| PU3 Order | 192.168.137.20:8083 | Thái |
| PU4 Inventory + Redis | 192.168.137.98:8084 / 6379 | Sang |
| Frontend | 192.168.137.41:3000 | Trường |

### Thứ tự đọc bắt buộc

1. `ai-agent/README.md` — index tổng thể.
2. `ai-agent/core/system.prompt.md` — vai trò + nguyên tắc bất biến.
3. `ai-agent/context/architecture.md` — sơ đồ SBA + luồng checkout.
4. `ai-agent/context/api.md` — 6 endpoint cố định.
5. `ai-agent/context/data-grid.md` — Redis schema.
6. `ai-agent/core/rules/sba.md` — quy tắc Space-Based bắt buộc.
7. `ai-agent/workflows/demo-flow.md` — kịch bản demo bắt buộc khi báo cáo.

**Quy tắc**: chỉ đọc file liên quan task hiện tại. Không đọc hết.

### Cấm bất biến

- ❌ **Không** thêm `spring-boot-starter-data-jpa`, `jdbc`, `mysql`, `postgresql`, `h2` vào pom.xml.
- ❌ **Không** sửa folder của member khác (Huy không được sửa `pu2-cart/`, etc).
- ❌ **Không** hardcode IP của PU khác trong code; dùng env var.
- ❌ **Không** dùng `RedisTemplate.get → set` cho stock; phải `decrement()` atomic.

---

## 1. Think Before Coding

**Don't assume. Don't hide confusion. Surface tradeoffs.**

- State your assumptions explicitly. If uncertain, ask.
- If multiple interpretations exist, present them — don't pick silently.
- If a simpler approach exists, say so. Push back when warranted.

## 2. Simplicity First

**Minimum code that solves the problem. Nothing speculative.**

- No features beyond what was asked.
- No abstractions for single-use code.
- No "flexibility" that wasn't requested.
- No error handling for impossible scenarios.

Đặc thù project này:
- Không cần Saga, Kafka, Eureka, Config Server. Đề bài không yêu cầu.
- Không cần auth. `userId` hardcode `"u1"` ở phase MVP.
- Không cần lưu DB persistent. Redis là source of truth.

## 3. Surgical Changes

**Touch only what you must. Clean up only your own mess.**

- Don't refactor adjacent code that isn't broken.
- Match existing style.
- Remove orphans your changes created; don't delete pre-existing dead code unless asked.

## 4. Goal-Driven Execution

Mỗi task → biến thành goal có thể verify:

- "Add /products endpoint" → "curl http://localhost:8081/products trả về 10 sản phẩm với schema đúng `context/api.md`".
- "Fix checkout race" → "chạy 50 concurrent checkout cùng productId, total stock decrement = 50, không có giá trị âm".

---

## 5. Quy trình làm việc tóm tắt

1. **Đọc** `ai-agent/` các file liên quan.
2. **Cập nhật contract trước** (`context/api.md`, `context/data-grid.md`) nếu thêm endpoint hoặc Redis key mới.
3. **Code** theo `core/rules/coding.md` + `core/rules/naming.md`.
4. **Test** bằng `curl` trong workflow `demo-flow.md`.
5. **Báo team** nếu thay đổi ảnh hưởng FE hoặc PU khác.

---

## 6. Khi không hiểu

- Hỏi user (member đang làm việc với bạn) thay vì đoán.
- Đặc biệt khi: thêm dependency mới, thêm endpoint cross-PU, đổi Redis key đã có.
