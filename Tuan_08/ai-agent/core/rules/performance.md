# Performance Rules

Mục tiêu đề bài: **chịu 1000+ req/s, low latency, không nghẽn DB**.

## Redis access patterns

- Đọc list products: `HVALS products:all` — O(N) nhưng N=10, OK.
- ❌ Không `KEYS *` hay `SCAN` trên hot path (chậm).
- Pipeline khi cần ghi nhiều key liên tiếp (vd: PU3 checkout nhiều item).

## Spring Boot tuning

- Tomcat connector mặc định 200 thread đủ cho 1000 req/s nếu mỗi request < 50ms.
- Lettuce (Redis client) là async non-blocking — không cần đổi.
- Không log INFO trong hot path nếu test load (chuyển log sang DEBUG).

## Frontend

- ProductsPage chỉ fetch 1 lần (không poll).
- ProductDetailPage poll `/stock/:id` 2s — nhẹ vì chỉ trả 1 integer.
- Cấm poll `/products` (data ít đổi).

## Load test

- Goal demo: `ab -n 1000 -c 50 http://192.168.137.157:8081/products` → mean response < 50ms.
