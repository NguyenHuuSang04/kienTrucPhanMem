# Optimize

## Khi dùng
Demo load test fail mục tiêu < 50ms, hoặc Redis MONITOR thấy quá nhiều round-trip.

## Các bước
1. Profile: `ab -n 1000 -c 50 <endpoint>` xem mean response.
2. `redis-cli -h 192.168.137.98 MONITOR` 5 giây → đếm số command / request.
3. Áp `core/rules/performance.md`:
   - Pipeline nhiều command thành 1.
   - Dùng `HVALS` thay nhiều `HGET`.
   - Bỏ log INFO trong hot path.
4. Optional: thêm Caffeine cache 5s cho `/products`.
5. Re-run load test → confirm < 50ms.
