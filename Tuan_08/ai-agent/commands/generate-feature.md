# Generate Feature

## Khi dùng
Thêm 1 endpoint mới vào PU của bạn.

## Các bước
1. Đọc `context/api.md` xem endpoint trùng không.
2. Áp template `core/templates/feature.md`.
3. Cập nhật contract trước (`context/api.md`, `context/data-grid.md` nếu cần).
4. Code Controller → Service → Repository.
5. Test bằng `curl`.
6. Báo Trường (FE) cập nhật `api/*.ts` nếu là endpoint FE phải gọi.
