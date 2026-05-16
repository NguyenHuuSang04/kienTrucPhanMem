# PU3 — Order Service (Thái)

Xử lý checkout: đọc cart trực tiếp từ Redis, gọi PU4 decrement stock, lưu order Hash, xóa cart.

## Endpoints

| Method | Path | Mô tả |
|---|---|---|
| POST | `/checkout` body `{userId}` | Tạo đơn |
| GET | `/orders/{userId}` | List đơn |
| GET | `/health` | Health |

## Lỗi

- 400 `EMPTY_CART` — cart rỗng.
- 409 `OUT_OF_STOCK:p001` — ít nhất 1 item hết. Đã rollback các DECR trước đó.
- 503 `INVENTORY_UNREACHABLE` — không gọi được PU4 (Sang offline?).

## Chạy

```powershell
docker compose up -d --build
curl http://localhost:8083/health
```

Chi tiết: xem `docs/run/thai.md`.
