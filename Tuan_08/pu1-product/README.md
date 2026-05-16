# PU1 — Product Service (Huy)

Listing + chi tiết sản phẩm. Đọc Redis hash `products:all`. Không gọi DB.

## Endpoints

| Method | Path | Mô tả |
|---|---|---|
| GET | `/products` | List 10 sản phẩm |
| GET | `/products/{id}` | Chi tiết 1 sản phẩm |
| POST | `/admin/seed` | Seed 10 product + stock=100 vào Redis (idempotent) |
| GET | `/health` | Health check |

## Chạy

```powershell
# Cần Sang đã chạy Redis ở 192.168.137.98:6379
docker compose up -d --build

curl http://localhost:8081/health
curl -X POST http://localhost:8081/admin/seed
curl http://localhost:8081/products
```

Chi tiết: xem `docs/run/huy.md`.
