# PU2 — Cart Service (Hiền)

Quản lý giỏ hàng. Lưu Redis hash `cart:{userId}`.

## Endpoints

| Method | Path | Mô tả |
|---|---|---|
| POST | `/cart/add` body `{userId, productId, quantity}` | Thêm sản phẩm |
| GET | `/cart?userId=...` | Lấy cart |
| DELETE | `/cart/{userId}` | Xóa cart |
| GET | `/health` | Health |

## Chạy

```powershell
docker compose up -d --build
curl http://localhost:8082/health
```

Chi tiết: xem `docs/run/hien.md`.
