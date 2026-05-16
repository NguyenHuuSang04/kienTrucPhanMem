# Bugfix Flow

## Bước 1 — Tái hiện

- Reproduce bằng `curl` chính xác — copy command vào template `core/templates/bugfix.md`.
- Nếu lỗi cross-PU: chạy `redis-cli -h 192.168.137.98 MONITOR` để xem flow Redis.

## Bước 2 — Khoanh vùng

- Lỗi ở PU nào? (đọc log: `docker compose logs -f app`).
- Liên quan key Redis nào? `redis-cli -h 192.168.137.98 KEYS "<prefix>*"`.

## Bước 3 — Fix

- Sửa minimum code. Không refactor đi kèm (xem `CLAUDE.md` rule "Surgical Changes").
- Nếu fix yêu cầu đổi contract → quay lại `feature-flow.md` (cần thông báo team).

## Bước 4 — Verify

- Re-run command tái hiện → phải pass.
- Chạy lại demo flow đầy đủ (`workflows/demo-flow.md`) → không regress.

## Bước 5 — Ghi `memory/known-issues.md` nếu lỗi đến từ infra

Vd: "Windows Firewall block port 8082" → ghi vào known-issues để Hiền tránh lặp lại.
