# Demo Flow (BẮT BUỘC khi báo cáo)

## Pre-check (mọi PU đã UP)

```powershell
curl http://192.168.137.157:8081/health   # PU1
curl http://192.168.137.184:8082/health   # PU2
curl http://192.168.137.20:8083/health    # PU3
curl http://192.168.137.98:8084/health    # PU4
redis-cli -h 192.168.137.98 ping           # Redis
```

Tất cả phải trả `{status: "UP"}` hoặc `PONG`.

## Step 1 — Load danh sách sản phẩm từ Redis

```powershell
curl http://192.168.137.157:8081/products
```

→ Trả 10 sản phẩm `p001`–`p010`.

## Step 2 — Add to cart

```powershell
curl -X POST http://192.168.137.184:8082/cart/add `
  -H "Content-Type: application/json" `
  -d '{"userId":"u1","productId":"p001","quantity":2}'

curl -X POST http://192.168.137.184:8082/cart/add `
  -H "Content-Type: application/json" `
  -d '{"userId":"u1","productId":"p002","quantity":1}'

curl "http://192.168.137.184:8082/cart?userId=u1"
```

→ Trả `{ items: { p001: 2, p002: 1 } }`.

## Step 3 — Check stock TRƯỚC checkout

```powershell
curl http://192.168.137.98:8084/stock/p001   # 100
curl http://192.168.137.98:8084/stock/p002   # 100
```

## Step 4 — Checkout

```powershell
curl -X POST http://192.168.137.20:8083/checkout `
  -H "Content-Type: application/json" `
  -d '{"userId":"u1"}'
```

→ Trả `{ orderId, total, items, createdAt }`.

## Step 5 — Stock GIẢM ngay

```powershell
curl http://192.168.137.98:8084/stock/p001   # 98 (giảm 2)
curl http://192.168.137.98:8084/stock/p002   # 99 (giảm 1)
```

## Step 6 — Cart đã rỗng

```powershell
curl "http://192.168.137.184:8082/cart?userId=u1"
```

→ Trả `{ items: {} }`.

## Step 7 — Load test (bonus)

Cài `ab` (Windows: dùng [Apache Lounge](https://www.apachelounge.com/download/) hoặc Docker):

```powershell
docker run --rm jordi/ab -n 1000 -c 50 http://192.168.137.157:8081/products
```

→ `Time per request` < 50ms.

## Step 8 — Demo scale (bonus)

Trên máy Huy, mở terminal thứ 2:

```powershell
cd pu1-product
$env:SERVER_PORT="8085"
./mvnw spring-boot:run
```

→ Có 2 instance PU1 (8081 + 8085) cùng đọc 1 Redis. Bonus điểm scale.
