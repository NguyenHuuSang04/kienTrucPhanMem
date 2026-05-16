# PU4 — Inventory Service + Redis Host (Sang)

Quản lý tồn kho. **Sang là người chạy Redis** trong cùng compose stack này.

## Endpoints

| Method | Path | Mô tả |
|---|---|---|
| GET | `/stock/{productId}` | Lấy stock |
| POST | `/stock/decrement/{productId}?qty=N` | Giảm stock atomic (DECR Redis), 409 nếu hết |
| POST | `/stock/set/{productId}?qty=N` | Set stock (admin only) |
| GET | `/health` | Health |

## Chạy (BẮT BUỘC chạy đầu tiên trong session)

```powershell
docker compose up -d --build

# Confirm seeder đã chạy xong:
docker logs flashsale-redis-seeder

# Confirm Redis có data:
docker exec flashsale-redis redis-cli HLEN products:all   # phải = 10
docker exec flashsale-redis redis-cli GET stock:p001       # phải = 100

# Health PU4:
curl http://localhost:8084/health
curl http://localhost:8084/stock/p001
```

Chi tiết: xem `docs/run/sang.md`.
