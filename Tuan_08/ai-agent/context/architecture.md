# Kiến trúc — Space-Based

## Sơ đồ tổng thể

```
                  +------------------------------------------+
                  |          Frontend (React + Vite)         |
                  |          192.168.137.41:3000             |
                  |          (Trường)                        |
                  +----+--------+---------+---------+--------+
                       |        |         |         |
                  REST |   REST |    REST |    REST |
                       v        v         v         v
        +----------+  +-------+  +-------+  +-------------+
        | PU1      |  | PU2   |  | PU3   |  | PU4         |
        | Product  |  | Cart  |  | Order |  | Inventory   |
        | :8081    |  | :8082 |  | :8083 |  | :8084       |
        | Huy      |  | Hiền  |  | Thái  |  | Sang        |
        +----+-----+  +---+---+  +---+---+  +------+------+
             |            |          |             |
             |            |          | calls       |
             |            |          | /stock/decrement
             |            |          v             |
             |            |     (REST PU4)         |
             |            |                        |
             +------------+------------+-----------+
                          |            |
                          v            v
                  +-----------------------------+
                  |      Redis (Data Grid)      |
                  |    192.168.137.98:6379      |
                  |    (chạy chung máy Sang)    |
                  +-----------------------------+
```

## Vì sao Redis ở máy Sang?

- PU4 Inventory là service "ghi" nhiều nhất (mỗi checkout đều DECR stock). Đặt Redis cùng máy → giảm latency mạng cho path nóng.
- Sang chỉ cần 1 lệnh `docker compose up` là có cả Redis + Inventory PU + seed data sẵn.

## Luồng đặt hàng (checkout)

```
User --(1)--> FE: bấm "Đặt hàng"
FE   --(2)--> PU3 Order: POST /checkout {userId}
PU3  --(3)--> Redis: HGETALL cart:{userId}     (đọc trực tiếp Data Grid, không qua PU2)
PU3  --(4)--> PU4: POST /stock/decrement/{id}?qty=N   (lặp cho từng item)
PU4  --(5)--> Redis: DECR stock:{id} bằng N
PU4  --(6)--> nếu kết quả < 0 → INCR rollback + trả 409
PU4  --(7)--> 200 OK cho PU3
PU3  --(8)--> Redis: HSET order:{orderId}, LPUSH order:user:{userId}
PU3  --(9)--> Redis: DEL cart:{userId}
PU3  --(10)-> 200 {orderId, total} cho FE
FE   --(11)-> hiển thị "Đặt hàng thành công"
```

## Vì sao PU3 đọc Redis trực tiếp thay vì gọi PU2?

- Tránh thêm hop mạng cho data đã nằm trong Data Grid (đúng tinh thần SBA: "data chia sẻ nằm ở Memory Grid, mọi PU truy cập được").
- PU2 vẫn là owner ghi cart; PU3 chỉ là consumer đọc. Tránh xung đột bằng cách: sau checkout, PU3 xóa cart luôn.

## Scale-out (bonus điểm "demo scale")

- Clone PU1 sang port 8085 trên cùng máy → Nginx round-robin trước 2 instance.
- Cả 2 instance cùng đọc 1 Redis → không lệch dữ liệu.
- Đây là điểm cốt lõi của SBA: PU stateless, có thể clone tùy ý.
