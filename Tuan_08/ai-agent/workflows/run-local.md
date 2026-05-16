# Run Local

## Mode A — Chỉ chạy PU của mình (dev nhanh)

Khi đang code dở, chưa muốn build Docker:

```powershell
cd pu1-product   # hoặc pu2-cart, pu3-order, pu4-inventory
$env:REDIS_HOST="192.168.137.98"
./mvnw spring-boot:run
```

Yêu cầu: Sang đã chạy Redis (xem Mode C).

## Mode B — Docker compose của mình

Khi muốn chạy giống production:

```powershell
cd pu1-product
docker compose up -d --build
docker compose logs -f app
```

## Mode C — Chạy Redis (chỉ Sang)

Sang chạy đầu tiên trong session:

```powershell
cd pu4-inventory
docker compose up -d --build
docker compose logs -f redis-seeder   # confirm seed xong rồi exit
redis-cli -h localhost ping            # PONG
redis-cli -h localhost HVALS products:all  # 10 sản phẩm
```

## Mode D — Tất cả trên 1 máy (khi không có LAN)

Một mình test toàn bộ system: dùng compose tổng tại `docker-compose.all.yml` ở root:

```powershell
docker compose -f docker-compose.all.yml up -d --build
# FE: cd frontend && npm run dev
```

Khi đó FE gọi `localhost:8081/8082/8083/8084` thay vì IP LAN. Cấu hình qua `.env.local`.
