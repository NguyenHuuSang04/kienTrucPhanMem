# Travel Booking SOA Backend

Backend cho bài tập **Orchestration-Driven SOA**. Hệ thống gồm 5 service REST độc lập:

- **Orchestrator Service**: điều phối toàn bộ flow, là service duy nhất Frontend gọi.
- **User Service**: đăng nhập và lấy thông tin người dùng.
- **Tour Service**: danh sách tour và chi tiết tour.
- **Booking Service**: tạo booking và cập nhật trạng thái booking.
- **Payment Service**: thanh toán booking, kết quả random `SUCCESS` hoặc `FAIL`.

Không cần cài thêm package. Chỉ cần Node.js 18+.

## Sơ Đồ LAN

| Thành viên | IP | Service |
| --- | --- | --- |
| Trường | `10.11.58.156` | Orchestrator `8080`, User `8081` |
| Huy | `10.11.58.16` | Booking `8083`, Payment `8084` |
| Sang | `10.11.58.22` | Tour `8082` |
| Hiền | `10.11.58.17` | Frontend `3000` |

Tất cả backend service lắng nghe trên `0.0.0.0` để các máy cùng Wi-Fi có thể gọi được.

Nếu Windows Firewall hỏi quyền truy cập cho `node.exe`, chọn **Allow access** cho **Private networks**.

## Thứ Tự Chạy

Nên chạy theo thứ tự:

1. Huy chạy Booking và Payment.
2. Sang chạy Tour.
3. Trường chạy User rồi Orchestrator.
4. Hiền chạy Frontend.

## Máy Trường - Orchestrator + User

Mở terminal 1:

```powershell
cd backend
node services/user-service/index.js
```

Mở terminal 2:

```powershell
cd backend
node services/orchestrator-service/index.js
```

Orchestrator mặc định gọi các service:

- User: `http://10.11.58.156:8081`
- Tour: `http://10.11.58.22:8082`
- Booking: `http://10.11.58.16:8083`
- Payment: `http://10.11.58.16:8084`

## Máy Huy - Booking + Payment

Mở terminal 1:

```powershell
cd backend
node services/booking-service/index.js
```

Mở terminal 2:

```powershell
cd backend
node services/payment-service/index.js
```

## Máy Sang - Tour

Mở terminal:

```powershell
cd backend
node services/tour-service/index.js
```

## API Cho Frontend

Frontend chỉ gọi Orchestrator:

```text
http://10.11.58.156:8080
```

### Login

```http
POST http://10.11.58.156:8080/login
Content-Type: application/json

{
  "email": "truong@example.com",
  "password": "123456"
}
```

### Lấy Danh Sách Tour

```http
GET http://10.11.58.156:8080/tours
```

### Lấy Chi Tiết Tour

```http
GET http://10.11.58.156:8080/tours/t1
```

### Đặt Tour

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

Payment Service random kết quả thanh toán, nên response có thể thành công hoặc thất bại.

## Tài Khoản Demo

| Email | Password | User ID |
| --- | --- | --- |
| `truong@example.com` | `123456` | `u1` |
| `huy@example.com` | `123456` | `u2` |
| `sang@example.com` | `123456` | `u3` |
| `hien@example.com` | `123456` | `u4` |

## Tour Demo

| Tour ID | Tên tour | Giá | Số chỗ |
| --- | --- | --- | --- |
| `t1` | Đà Lạt 3 ngày 2 đêm | `2.500.000 VND` | `12` |
| `t2` | Phú Quốc biển xanh | `4.200.000 VND` | `8` |
| `t3` | Hà Nội - Hạ Long | `5.100.000 VND` | `15` |

## Kiểm Tra Nhanh

Mở trình duyệt hoặc Postman:

```text
http://10.11.58.156:8080/health
http://10.11.58.156:8081/health
http://10.11.58.22:8082/health
http://10.11.58.16:8083/health
http://10.11.58.16:8084/health
```

## Log Khi Demo

Mỗi service sẽ in log ra terminal theo format:

```text
[timestamp] [service-name] [request-id] message {json}
```

Khi gọi `POST /book-tour`, Orchestrator sẽ log rõ 5 bước:

```text
STEP 1/5 - Validate user via User Service
STEP 2/5 - Get tour information via Tour Service
STEP 3/5 - Create booking via Booking Service
STEP 4/5 - Process payment via Payment Service
STEP 5/5 - Update booking status via Booking Service
```

Các service nghiệp vụ cũng log hành động riêng như `LOGIN`, `GET tour detail`, `CREATE booking`, `PROCESS payment`.

## Test Một Máy

Khi muốn chạy toàn bộ backend trên cùng một máy:

```powershell
cd backend
.\run-all-local.ps1
```

Hoặc tự chạy Orchestrator với các service local:

```powershell
$env:USER_SERVICE_URL="http://localhost:8081"
$env:TOUR_SERVICE_URL="http://localhost:8082"
$env:BOOKING_SERVICE_URL="http://localhost:8083"
$env:PAYMENT_SERVICE_URL="http://localhost:8084"
node services/orchestrator-service/index.js
```
