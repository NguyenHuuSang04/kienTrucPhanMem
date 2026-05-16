# Fix Bug

## Khi dùng
Phát hiện endpoint trả sai, request lỗi 5xx, hoặc data lệch.

## Các bước
1. Reproduce bằng `curl` chính xác.
2. Áp template `core/templates/bugfix.md` ghi lại trace.
3. `redis-cli -h 192.168.137.98 MONITOR` xem hành vi Redis.
4. Sửa minimum code. Không refactor đi kèm.
5. Re-run reproduce → pass.
6. Chạy lại `workflows/demo-flow.md` → không regress.
7. Nếu lỗi infra, ghi vào `memory/known-issues.md`.
