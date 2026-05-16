# ai-agent/ — Project Context cho Flash Sale SBA

Đây là **single source of truth** cho mọi quy ước, kiến trúc, quy trình của project. Khi bạn (hoặc AI assistant) bắt đầu một phiên làm việc trên repo này, hãy đọc theo thứ tự dưới đây.

## Project tóm tắt

| Mục | Giá trị |
|---|---|
| Tên | Flash Sale System |
| Kiến trúc | Space-Based Architecture (Chương 7) |
| Stack BE | Spring Boot 3.3 + Java 21 + Maven |
| Stack FE | Vite + React 19 + TypeScript + TailwindCSS v4 |
| Data Grid | Redis 7 (single instance ở máy Sang) |
| Triển khai | Docker Compose, mỗi member 1 service trên LAN 192.168.137.x |

## Bảng phân công

| Service | IP:Port | Member |
|---|---|---|
| PU1 Product | 192.168.137.157:8081 | Huy |
| PU2 Cart | 192.168.137.184:8082 | Hiền |
| PU3 Order | 192.168.137.20:8083 | Thái |
| PU4 Inventory + Redis | 192.168.137.98:8084 / 6379 | Sang |
| Frontend | 192.168.137.41:3000 | Trường |

## Thứ tự đọc

1. `core/system.prompt.md` — Vai trò AI / nguyên tắc bất biến.
2. `context/architecture.md` — Sơ đồ + luồng checkout.
3. `context/api.md` — 6 endpoint chính.
4. `context/data-grid.md` — Schema Redis.
5. `context/network.md` — Bảng IP + check connectivity.
6. `context/stack.md` — Phiên bản dependency.
7. `core/rules/*.md` — Quy ước code, naming, SBA, security.
8. `workflows/*.md` — Quy trình feature, bugfix, run-local, demo.
9. `memory/*.md` — ADR, conventions, known issues.
10. `commands/*.md` — Lệnh tắt cho các tác vụ phổ biến.

## Nguyên tắc bất biến

- **Không gọi DB**. Chỉ Redis. Pom.xml không được có `spring-boot-starter-data-jpa`, `jdbc`, `mysql`, `postgresql`.
- **Mỗi PU độc lập**. Không import code của PU khác. Giao tiếp qua REST hoặc qua Redis Data Grid.
- **Atomic stock decrement**. PU4 dùng `DECR` (không phải GET → SET).
- **CORS chỉ mở cho FE**: `http://192.168.137.41:3000` và `http://localhost:3000`.
- **Không ai được sửa folder của member khác** mà không thông báo.
