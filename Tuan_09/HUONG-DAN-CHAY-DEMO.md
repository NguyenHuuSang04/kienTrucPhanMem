# Hướng Dẫn Chạy Và Kịch Bản Demo

Tài liệu này dùng cho nhóm 4 người chạy hệ thống Travel Booking theo mô hình **Orchestration-Driven SOA** trên LAN.

## 1. Phân Công Máy

| Thành viên | IP | Chạy gì | Port |
| --- | --- | --- | --- |
| Trường | `10.11.58.156` | Orchestrator Service, User Service | `8080`, `8081` |
| Huy | `10.11.58.16` | Booking Service, Payment Service | `8083`, `8084` |
| Sang | `10.11.58.22` | Tour Service | `8082` |
| Hiền | `10.11.58.17` | Frontend React | `3000` |

Quy tắc quan trọng:

- Frontend chỉ gọi Orchestrator tại `http://10.11.58.156:8080`.
- User, Tour, Booking, Payment không gọi chéo nhau.
- Orchestrator là service trung tâm điều phối toàn bộ flow.

## 2. Chuẩn Bị Trước Khi Chạy

Tất cả máy:

1. Kết nối cùng một Wi-Fi/LAN.
2. Kiểm tra IP đúng với phân công.
3. Cài Node.js 18+.
4. Nếu Windows Firewall hỏi quyền cho `node.exe`, chọn **Allow access** cho **Private networks**.

Kiểm tra Node:

```powershell
node -v
```

Nếu dùng PowerShell bị lỗi `npm.ps1 cannot be loaded`, dùng `npm.cmd` thay cho `npm`.

## 3. Thứ Tự Bật Hệ Thống

Nên bật theo thứ tự sau để Orchestrator gọi được các service phụ thuộc:

1. Huy bật Booking và Payment.
2. Sang bật Tour.
3. Trường bật User và Orchestrator.
4. Hiền bật Frontend.

## 4. Máy Huy - Booking + Payment

Mở terminal 1:

```powershell
cd backend
node services/booking-service/index.js
```

Kết quả mong đợi:

```text
Booking Service listening on http://0.0.0.0:8083
```

Mở terminal 2:

```powershell
cd backend
node services/payment-service/index.js
```

Kết quả mong đợi:

```text
Payment Service listening on http://0.0.0.0:8084
```

## 5. Máy Sang - Tour

Mở terminal:

```powershell
cd backend
node services/tour-service/index.js
```

Kết quả mong đợi:

```text
Tour Service listening on http://0.0.0.0:8082
```

## 6. Máy Trường - User + Orchestrator

Mở terminal 1:

```powershell
cd backend
node services/user-service/index.js
```

Kết quả mong đợi:

```text
User Service listening on http://0.0.0.0:8081
```

Mở terminal 2:

```powershell
cd backend
node services/orchestrator-service/index.js
```

Kết quả mong đợi:

```text
Orchestrator Service listening on http://0.0.0.0:8080
```

Orchestrator đang gọi:

```text
User    -> http://10.11.58.156:8081
Tour    -> http://10.11.58.22:8082
Booking -> http://10.11.58.16:8083
Payment -> http://10.11.58.16:8084
```

## 7. Máy Hiền - Frontend

Mở terminal:

```powershell
cd frontend
npm.cmd run dev
```

Kết quả mong đợi:

```text
Local:   http://localhost:3000
Network: http://10.11.58.17:3000
```

Mở trình duyệt:

```text
http://10.11.58.17:3000
```

Frontend mặc định gọi Orchestrator:

```text
http://10.11.58.156:8080
```

## 8. Kiểm Tra Hệ Thống Trước Khi Demo

Thực hiện bằng trình duyệt hoặc Postman.

Kiểm tra Orchestrator:

```text
http://10.11.58.156:8080/health
```

Kiểm tra User:

```text
http://10.11.58.156:8081/health
```

Kiểm tra Tour:

```text
http://10.11.58.22:8082/health
```

Kiểm tra Booking:

```text
http://10.11.58.16:8083/health
```

Kiểm tra Payment:

```text
http://10.11.58.16:8084/health
```

Kiểm tra Frontend gọi được Orchestrator:

```text
http://10.11.58.156:8080/tours
```

Nếu endpoint `/tours` trả về danh sách tour, hệ thống đã sẵn sàng demo.

## 9. Dữ Liệu Demo

### Tài Khoản

| Email | Password | User ID |
| --- | --- | --- |
| `truong@example.com` | `123456` | `u1` |
| `huy@example.com` | `123456` | `u2` |
| `sang@example.com` | `123456` | `u3` |
| `hien@example.com` | `123456` | `u4` |

### Tour

| Tour ID | Tên tour | Giá | Số chỗ |
| --- | --- | --- | --- |
| `t1` | Đà Lạt 3 ngày 2 đêm | `2.500.000 VND` | `12` |
| `t2` | Phú Quốc biển xanh | `4.200.000 VND` | `8` |
| `t3` | Hà Nội - Hạ Long | `5.100.000 VND` | `15` |

## 10. Kịch Bản Demo Giao Diện

### Bước 1 - Giới Thiệu Kiến Trúc

Nói ngắn gọn:

```text
Hệ thống áp dụng Orchestration-Driven SOA. Frontend không gọi trực tiếp các service nghiệp vụ.
Frontend chỉ gọi Orchestrator. Orchestrator lần lượt gọi User, Tour, Booking và Payment.
```

Chỉ ra các máy:

```text
Trường: Orchestrator + User
Huy: Booking + Payment
Sang: Tour
Hiền: Frontend
```

### Bước 2 - Mở Frontend

Trên máy Hiền mở:

```text
http://10.11.58.17:3000
```

Màn hình mong đợi:

- Hiển thị danh sách tour.
- Có các tour Đà Lạt, Phú Quốc, Hà Nội - Hạ Long.
- Dữ liệu này được lấy từ Tour Service thông qua Orchestrator.

### Bước 3 - Đăng Nhập

Bấm **Đăng nhập**.

Dùng tài khoản:

```text
truong@example.com
123456
```

Kết quả mong đợi:

- Đăng nhập thành công.
- Navbar hiển thị tên người dùng `Nguyễn Văn Trường`.

Giải thích:

```text
Frontend gửi POST /login đến Orchestrator.
Orchestrator chuyển tiếp sang User Service để xác thực.
```

### Bước 4 - Chọn Tour

Ở trang chủ, chọn tour:

```text
Đà Lạt 3 ngày 2 đêm
```

Kết quả mong đợi:

- Mở trang chi tiết tour.
- Hiển thị giá, số chỗ, mô tả tour.

Giải thích:

```text
Danh sách tour được lấy từ GET /tours qua Orchestrator.
Frontend không gọi trực tiếp Tour Service.
```

### Bước 5 - Đặt Tour

Chọn số lượng:

```text
2 khách
```

Bấm **Đặt ngay**.

Kết quả có 2 trường hợp vì Payment Service random:

Trường hợp thành công:

```text
Đặt tour thành công
Booking status: CONFIRMED
Payment status: SUCCESS
```

Trường hợp thất bại:

```text
Thanh toán thất bại
Booking status: PAYMENT_FAILED
Payment status: FAIL
```

Giải thích flow:

```text
Frontend -> Orchestrator: POST /book-tour
Orchestrator -> User Service: kiểm tra user
Orchestrator -> Tour Service: lấy thông tin tour
Orchestrator -> Booking Service: tạo booking
Orchestrator -> Payment Service: thanh toán
Orchestrator -> Booking Service: cập nhật trạng thái booking
Orchestrator -> Frontend: trả kết quả cuối cùng
```

### Bước 6 - Show Log Trên Terminal

Khi bấm **Đặt ngay**, cả nhóm có thể chỉ vào terminal của từng máy để chứng minh flow đang chạy thật.

Terminal máy Trường - Orchestrator sẽ thấy các dòng chính:

```text
[orchestrator-service] [ABC123] BOOK TOUR orchestration started
[orchestrator-service] [ABC123] STEP 1/5 - Validate user via User Service
[orchestrator-service] [ABC123] STEP 2/5 - Get tour information via Tour Service
[orchestrator-service] [ABC123] STEP 3/5 - Create booking via Booking Service
[orchestrator-service] [ABC123] STEP 4/5 - Process payment via Payment Service
[orchestrator-service] [ABC123] STEP 5/5 - Update booking status via Booking Service
[orchestrator-service] [ABC123] BOOK TOUR orchestration finished
```

Terminal máy Trường - User Service sẽ thấy:

```text
[user-service] [ABC123] VALIDATE user
[user-service] [ABC123] VALIDATE user success
```

Terminal máy Sang - Tour Service sẽ thấy:

```text
[tour-service] [ABC123] GET tour detail
[tour-service] [ABC123] GET tour detail success
```

Terminal máy Huy - Booking Service sẽ thấy:

```text
[booking-service] [ABC123] CREATE booking requested
[booking-service] [ABC123] CREATE booking success
[booking-service] [ABC123] UPDATE booking status requested
[booking-service] [ABC123] UPDATE booking status success
```

Terminal máy Huy - Payment Service sẽ thấy:

```text
[payment-service] [ABC123] PROCESS payment requested
[payment-service] [ABC123] PROCESS payment completed
```

Mỗi service cũng tự log request/response chung:

```text
Incoming request
Response sent
Request failed
```

`ABC123` là request id random, mỗi request sẽ khác nhau.

## 11. Kịch Bản Demo Bằng Postman

Nếu muốn demo API song song với giao diện, dùng các request sau.

### Request 1 - Health Check Orchestrator

```http
GET http://10.11.58.156:8080/health
```

Kết quả mong đợi:

```json
{
  "success": true,
  "service": "orchestrator-service"
}
```

### Request 2 - Lấy Danh Sách Tour

```http
GET http://10.11.58.156:8080/tours
```

Kết quả mong đợi:

```json
{
  "success": true,
  "tours": [
    {
      "id": "t1",
      "name": "Đà Lạt 3 ngày 2 đêm"
    }
  ]
}
```

### Request 3 - Login

```http
POST http://10.11.58.156:8080/login
Content-Type: application/json

{
  "email": "truong@example.com",
  "password": "123456"
}
```

Kết quả mong đợi:

```json
{
  "success": true,
  "user": {
    "id": "u1",
    "name": "Nguyễn Văn Trường"
  }
}
```

### Request 4 - Book Tour

```http
POST http://10.11.58.156:8080/book-tour
Content-Type: application/json

{
  "userId": "u1",
  "tourId": "t1",
  "quantity": 2,
  "paymentMethod": "BANK_TRANSFER"
}
```

Kết quả thành công mẫu:

```json
{
  "success": true,
  "message": "Đặt tour thành công",
  "booking": {
    "status": "CONFIRMED"
  },
  "payment": {
    "status": "SUCCESS"
  }
}
```

Kết quả thất bại mẫu:

```json
{
  "success": false,
  "message": "Đã tạo booking nhưng thanh toán thất bại",
  "booking": {
    "status": "PAYMENT_FAILED"
  },
  "payment": {
    "status": "FAIL"
  }
}
```

## 12. Lỗi Thường Gặp

### Không mở được Frontend từ máy khác

Kiểm tra frontend có chạy bằng host `0.0.0.0` chưa. Script hiện tại đã cấu hình:

```text
vite --port=3000 --host=0.0.0.0
```

Kiểm tra Windows Firewall trên máy Hiền.

### Frontend báo không kết nối được backend

Kiểm tra Orchestrator:

```text
http://10.11.58.156:8080/health
```

Kiểm tra file `.env.local` của frontend nếu có tạo:

```text
VITE_ORCHESTRATOR_URL="http://10.11.58.156:8080"
```

### Orchestrator lỗi khi đặt tour

Kiểm tra 4 service nghiệp vụ:

```text
http://10.11.58.156:8081/health
http://10.11.58.22:8082/health
http://10.11.58.16:8083/health
http://10.11.58.16:8084/health
```

### Payment thất bại

Đây là đúng yêu cầu bài tập. Payment Service random `SUCCESS` hoặc `FAIL`. Nếu muốn thấy case thành công, bấm đặt lại một lần nữa.

## 13. Câu Nói Kết Demo

```text
Qua demo có thể thấy Frontend chỉ gọi Orchestrator. Các service nghiệp vụ chỉ xử lý nhiệm vụ riêng và không gọi chéo nhau. Orchestrator điều phối toàn bộ quy trình đặt tour, đúng với mô hình Orchestration-Driven SOA.
```
