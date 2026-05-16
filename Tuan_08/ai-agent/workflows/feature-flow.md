# Feature Flow

Áp dụng khi thêm endpoint hoặc tính năng mới vào PU của mình.

## Bước 1 — Đọc context

- `context/api.md` — endpoint đã chốt? Có trùng không?
- `context/data-grid.md` — Redis key cần dùng đã có schema chưa?
- `core/rules/sba.md` — không vi phạm SBA?

## Bước 2 — Cập nhật contract trước, code sau

1. Thêm dòng vào `context/api.md` nếu là endpoint mới.
2. Thêm key vào `context/data-grid.md` nếu cần key Redis mới.
3. Báo cho Trường (FE) biết để cập nhật `api/*.ts` tương ứng.

## Bước 3 — Code

- Controller → Service → Repository theo `core/rules/coding.md`.
- DTO request có `@Valid`.
- Lỗi qua `@RestControllerAdvice` chung của PU (`config/GlobalExceptionHandler.java`).

## Bước 4 — Verify

- Build: `./mvnw clean package -DskipTests`.
- Run: `./mvnw spring-boot:run` hoặc `docker compose up --build`.
- Test bằng `curl` (xem `workflows/demo-flow.md`).
- Test CORS từ FE: mở browser console ở `http://localhost:3000`, gọi endpoint.

## Bước 5 — Commit

- Branch `feat/<pu>-<feature>` (vd: `feat/product-search`).
- Commit message: `[PU<N>] <feature mô tả ngắn>`.
- Tag teammates trong PR description nếu cần họ cập nhật contract.
