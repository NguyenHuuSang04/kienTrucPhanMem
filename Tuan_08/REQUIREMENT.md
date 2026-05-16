Bài Tập Thực Hành
Kiến Trúc và Thiết Kế Phần Mềm

Buổi 7 — SPACE-BASED ARCHITECTURE

Bài toán: Hệ thống Flash Sale (Bán hàng sốc – chịu tải cao)

Một hệ thống bán hàng flash sale (giống Shopee/Lazada) cần:

• Chịu tải cao (1000+ request/s)
• Tránh nghẽn database
• Xử lý nhanh (low latency)

❖ Yêu cầu 5 chức năng chính:

• Xem danh sách sản phẩm
• Xem chi tiết sản phẩm
• Thêm vào giỏ hàng
• Đặt hàng (checkout)
• Giảm tồn kho (real-time)

❖ Yêu cầu kiến trúc:

Áp dụng Space-Based Architecture:

❖ Nguyên lý:

• Hạn chế DB (tránh bottleneck)
• Dữ liệu nằm trong Memory Grid (Data Grid)
• Xử lý tại Processing Unit (PU)

❖ Thành phần chính

• Processing Unit (PU) = Service xử lý + cache local
• Data Grid = Redis / Hazelcast (chia sẻ dữ liệu RAM)
• Messaging (optional)

Phân công 5 người:
❖ Người 1– Frontend (ReactJS)

UI:

• Danh sách sản phẩm
• Giỏ hàng
• Đặt hàng

Gọi API vào Processing Unit (PU)

❖ Người 2 – Product Processing Unit (PU1)

API:

Data:

• GET /products
• GET /products/{id}

• Load từ Data Grid (Redis)

Không đọc DB trực tiếp

## ❖ Người 3 – Cart Processing Unit (PU2)

Bộ môn: Kỹ thuật phần mềm

7

## Bài tập thực hành Kiến trúc và thiết kế phần mềm

API:

Data:

• POST /cart/add
• GET /cart

• Lưu trong Data Grid (session/cart)

❖ Người 4 – Order Processing Unit (PU3)

API:

• POST / checkout

Xử lý:

• Lấy cart từ Data Grid
• Tạo order
• Publish event (optional)

❖ Người 5 – Inventory Processing Unit (PU4)

API:

• GET /stock/{productId}

Xử lý:

• Khi checkout

- Giảm tồn kho trực tiếp trên Data Grid

Không gọi DB

Mô hình triển khai trên LAN:

Service

IP

Redis (Data Grid)
PU1 – Product
PU2 – Cart
PU3 – Order
PU4 – Inventory
Frontend

192.168.?.?:6379
192.168.?.?:8081
192.168.?.?:8082
192.168.?.?:8083
192.168.?.?:8084
192.168.?.?:3000

Luồng xử lý chính

❖ Luồng đặt hàng

1.  User chọn sản phẩm → add to cart

2.  Cart lưu vào Data Grid (Redis)

3.  User checkout

4.  Order PU:

o Lấy cart từ Redis

---

Bộ môn: Kỹ thuật phần mềm

8

## Bài tập thực hành Kiến trúc và thiết kế phần mềm

o Gọi Inventory (hoặc trực tiếp Redis)
o Giảm stock

5.  Trả kết quả ngay (KHÔNG chờ DB)

Kịch bản Test (BẮT BUỘC DEMO)

1.  Load danh sách sản phẩm từ Redis
2.  Add to card
3.  Checkout
4.  Stock giảm ngay lập tức
5.  Không bị chậm khi nhiều request

Bonus (nếu làm nhanh)

1.  Dùng Hazelcast thay Redis
2.  Implement locking (SETNX)
3.  Thêm Queue xử lý async
4.  Simulate load test (Postman Runner)

Tiêu chí chấm điểm

Tiêu chí

Đúng Space-Based
Không phụ thuộc DB
Dùng Data Grid đúng
Flow nhanh, không nghẽn
Demo scale (clone PU)

Điểm

3
2.5
2
1.5
1

---

Bộ môn: Kỹ thuật phần mềm

9
