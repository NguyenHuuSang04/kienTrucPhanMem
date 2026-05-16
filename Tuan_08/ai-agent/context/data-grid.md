# Redis Data Grid Schema

Toàn bộ state của hệ thống nằm trong Redis này. Không có DB.

| Key | Type | Owner (write) | Reader | TTL | Mô tả |
|---|---|---|---|---|---|
| `products:all` | Hash | seed-script (1 lần), PU1 `/admin/seed` | PU1, PU3 | none | Field = productId, value = JSON `{id,name,price,image,description}`. |
| `stock:{productId}` | String (integer) | seed-script, PU4 | PU4 | none | Tồn kho. Dùng `DECR/INCR` atomic. |
| `cart:{userId}` | Hash | PU2 | PU2, PU3 | 1 giờ | Field = productId, value = quantity (int). |
| `order:{orderId}` | Hash | PU3 | PU3 | none | `userId, items (JSON), total, createdAt`. |
| `order:user:{userId}` | List | PU3 | PU3 | none | LPUSH orderId vào đầu danh sách. |
| `seq:order` | String (integer) | PU3 | PU3 | none | INCR để sinh orderId tự tăng. |

## Quy ước productId

Seed sẵn 10 sản phẩm: `p001` → `p010`. Stock khởi tạo = 100 cho mỗi sản phẩm.

## Ví dụ command

```
# Xem toàn bộ products
redis-cli -h 192.168.137.98 HVALS products:all

# Xem stock 1 sản phẩm
redis-cli -h 192.168.137.98 GET stock:p001

# Xem cart user u1
redis-cli -h 192.168.137.98 HGETALL cart:u1

# Reset toàn bộ (cẩn thận!)
redis-cli -h 192.168.137.98 FLUSHDB
```

## Cấm

- Không tạo key trùng namespace (vd: tạo `cart:metadata:u1` — bị nhập nhằng với `cart:u1`).
- Không SCAN trong production path (chậm). PU1 list products dùng `HVALS products:all`.
- Không lưu binary lớn (ảnh thật) trong Redis. Field `image` chỉ chứa URL.
