# 🚀 HƯỚNG DẪN TEST HỆ THỐNG RABBITMQ NÂNG CAO

## Tính năng mới đã thêm:
✅ **Priority Queue** - Đơn hàng VIP được xử lý trước
✅ **Order Status Tracking** - Theo dõi trạng thái đơn hàng
✅ **Retry Mechanism** - Tự động thử lại khi lỗi (tối đa 3 lần)
✅ **Multiple Queue Types** - VIP Queue, Normal Queue, Status Queue, Retry Queue, DLQ
✅ **Bulk Order Creation** - Tạo nhiều đơn hàng cùng lúc

---

## 📋 CHUẨN BỊ

### 1. Stop containers cũ và restart
```bash
docker compose down
docker compose up --build
```

### 2. Kiểm tra logs
```bash
# Terminal 1: Xem producer logs
docker logs -f producer

# Terminal 2: Xem consumer logs
docker logs -f consumer
```

Bạn sẽ thấy:
- Producer: `🚀 Advanced Producer API listening on port 3000`
- Consumer: `✅ Consumer connected to RabbitMQ`

---

## 🧪 TEST CASES VỚI POSTMAN

### ✅ TEST 1: Tạo đơn hàng THƯỜNG (Normal)

**Endpoint:** `POST http://localhost:3000/order/create`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "orderId": "ORD-001",
  "customerName": "Nguyen Van A",
  "items": ["Laptop Dell", "Mouse Logitech"],
  "totalAmount": 1500,
  "isVIP": false
}
```

**Kết quả mong đợi:**
- Response: `"queue": "order_queue_normal"`, `"priority": 5`
- Consumer logs: Đợi ~3-5s → `✅ Order ORD-001 completed successfully!`

---

### ⭐ TEST 2: Tạo đơn hàng VIP (Ưu tiên cao)

**Endpoint:** `POST http://localhost:3000/order/create`

**Body:**
```json
{
  "orderId": "VIP-001",
  "customerName": "Tran Thi B",
  "items": ["iPhone 15 Pro Max", "MacBook Pro"],
  "totalAmount": 5000,
  "isVIP": true
}
```

**Kết quả mong đợi:**
- Response: `"queue": "order_queue_vip"`, `"priority": 10`
- Consumer logs: `🌟 === VIP ORDER PROCESSING ===` → Xử lý nhanh hơn (2s) → `✅ VIP Order VIP-001 completed!`

---

### 🔥 TEST 3: So sánh Priority (Gửi nhiều đơn liên tiếp)

**Gửi lần lượt 3 requests này (nhanh nhanh):**

**Request 1 - Priority thấp:**
```json
{
  "orderId": "LOW-001",
  "customerName": "Customer Low",
  "items": ["Book"],
  "totalAmount": 50,
  "isVIP": false
}
```

**Request 2 - Priority cao:**
```json
{
  "orderId": "HIGH-001",
  "customerName": "Customer High",
  "items": ["Gaming PC"],
  "totalAmount": 3000,
  "isVIP": false
}
```

**Request 3 - VIP:**
```json
{
  "orderId": "VIP-002",
  "customerName": "VIP Customer",
  "items": ["Diamond Ring"],
  "totalAmount": 10000,
  "isVIP": true
}
```

**Kết quả mong đợi:**
- Consumer xử lý theo thứ tự: **VIP-002 → HIGH-001 → LOW-001**
- Thứ tự này khác với thứ tự gửi!

---

### 🔄 TEST 4: Retry Mechanism (Test tự động)

**Endpoint:** `POST http://localhost:3000/order/create`

**Body:**
```json
{
  "orderId": "RETRY-001",
  "customerName": "Test Retry",
  "items": ["Test Item"],
  "totalAmount": 500,
  "isVIP": false
}
```

**Kết quả mong đợi:**
- Consumer có 30% cơ hội fail ngẫu nhiên
- Nếu fail: Consumer tự động retry sau 10 giây
- Xem logs: `🔄 Retry attempt 1/3 - Will retry in 10s`
- Sau 10s: Consumer sẽ thử lại tự động
- Nếu fail 3 lần: `💀 Max retries reached - Sending to DLQ`

---

### ❌ TEST 5: Hủy đơn hàng

**Endpoint:** `POST http://localhost:3000/order/cancel`

**Body:**
```json
{
  "orderId": "ORD-001",
  "reason": "Customer changed mind"
}
```

**Kết quả mong đợi:**
- Consumer logs: `❌ Order ORD-001 cancelled`
- Reason: `Customer changed mind`

---

### 📊 TEST 6: Cập nhật trạng thái đơn hàng

**Endpoint:** `POST http://localhost:3000/order/status`

**Body:**
```json
{
  "orderId": "VIP-001",
  "status": "shipping",
  "note": "Package picked up by delivery service"
}
```

**Kết quả mong đợi:**
- Consumer logs: `📝 Order VIP-001 status: shipping`
- Note: `Package picked up by delivery service`

---

### 🚀 TEST 7: Tạo nhiều đơn hàng cùng lúc (Bulk)

**Endpoint:** `POST http://localhost:3000/order/bulk`

**Body:**
```json
{
  "orders": [
    {
      "orderId": "BULK-001",
      "customerName": "Customer 1",
      "items": ["Item 1"],
      "totalAmount": 100,
      "isVIP": false
    },
    {
      "orderId": "BULK-002",
      "customerName": "Customer 2",
      "items": ["Item 2"],
      "totalAmount": 2000,
      "isVIP": false
    },
    {
      "orderId": "BULK-VIP-001",
      "customerName": "VIP Customer",
      "items": ["Premium Item"],
      "totalAmount": 5000,
      "isVIP": true
    }
  ]
}
```

**Kết quả mong đợi:**
- Response: `"count": 3`
- Consumer xử lý theo priority: VIP trước, high amount sau, low amount cuối

---

### ❌ TEST 8: Validation Error (Thiếu field bắt buộc)

**Endpoint:** `POST http://localhost:3000/order/create`

**Body:**
```json
{
  "orderId": "ERROR-001",
  "items": ["Item"]
}
```

**Kết quả mong đợi:**
- Response 400: `"error": "Missing required fields: orderId, customerName, items, totalAmount"`

---

### 💀 TEST 9: Dead Letter Queue (DLQ)

Consumer có random failure rate, nếu 1 đơn hàng fail 3 lần liên tiếp:

**Xem logs:**
```bash
docker logs -f consumer
```

**Kết quả mong đợi:**
```
❌ Order processing failed: Inventory check failed
🔄 Retry attempt 1/3 - Will retry in 10s
... (10 giây sau)
❌ Order processing failed: Inventory check failed
🔄 Retry attempt 2/3 - Will retry in 10s
... (10 giây sau)
❌ Order processing failed: Inventory check failed
🔄 Retry attempt 3/3 - Will retry in 10s
... (10 giây sau)
💀 Max retries reached - Sending to DLQ
💀 === DEAD LETTER QUEUE ===
❌ Failed order: {...}
```

---

### 🏥 TEST 10: Health Check

**Endpoint:** `GET http://localhost:3000/health`

**Kết quả mong đợi:**
```json
{
  "status": "healthy",
  "rabbitmq": "connected",
  "timestamp": "2026-01-24T..."
}
```

---

## 🎯 DEMO CHO CÔ - FLOW HOÀN CHỈNH

### Scenario: E-commerce Order Processing

1. **Tạo đơn hàng thường:**
   - POST `/order/create` với `isVIP: false`, `totalAmount: 500`
   - Xem consumer xử lý chậm (~5s)

2. **Tạo đơn hàng VIP khi đang xử lý đơn thường:**
   - POST `/order/create` với `isVIP: true`
   - VIP được xử lý ngay lập tức, đơn thường phải chờ!

3. **Cập nhật trạng thái:**
   - POST `/order/status` với `status: "processing"`
   - POST `/order/status` với `status: "completed"`

4. **Test retry:**
   - Gửi nhiều đơn hàng, một số sẽ fail và retry tự động

5. **Xem database:**
   - Consumer in ra database mỗi 15 giây
   - Thấy tất cả orders với status

---

## 🌐 RABBITMQ MANAGEMENT UI

Mở trình duyệt: `http://localhost:15672`

**Login:**
- Username: `user`
- Password: `password`

**Xem:**
- **Queues tab:** 
  - `order_queue_vip` - VIP orders
  - `order_queue_normal` - Normal orders (có priority)
  - `order_status_queue` - Status updates
  - `order_retry_queue` - Failed orders waiting to retry
  - `order_queue.dlq` - Dead letters (failed 3 times)

- **Connections:** Producer và Consumer connections
- **Channels:** Message channels đang hoạt động

---

## 🎓 ĐIỂM KHÁC BIỆT SO VỚI CODE MẪU CỦA CÔ

| Tính năng | Code mẫu | Code nâng cao |
|-----------|----------|---------------|
| Queue | 1 queue đơn giản | 5 queues chuyên biệt |
| Priority | Không | Có (0-10) |
| Retry | Không | Có (3 lần, 10s interval) |
| Status tracking | Không | Có (riêng queue) |
| VIP handling | Không | Có (queue riêng) |
| Bulk operations | Không | Có |
| Error handling | Đơn giản | Phức tạp với DLQ |
| Logging | Basic | Chi tiết với emoji |

---

## 📝 KẾT LUẬN

Code này demo được:
- ✅ Event-driven architecture với RabbitMQ
- ✅ Producer-Consumer pattern
- ✅ Priority Queue
- ✅ Retry mechanism với exponential backoff
- ✅ Dead Letter Queue handling
- ✅ Multiple queue types
- ✅ Real-world e-commerce scenario

**Đủ để demo và giải thích cho cô hiểu sâu về Message Queue!** 🎉
