# API Contract

Tất cả response dùng JSON. Lỗi trả `{ "error": "...", "code": "..." }` với HTTP status phù hợp.

## PU1 — Product (`http://192.168.137.157:8081`)

| Method | Path | Body | Response |
|---|---|---|---|
| GET | `/products` | — | `[{id, name, price, image, description}]` |
| GET | `/products/{id}` | — | `{id, name, price, image, description}` |
| POST | `/admin/seed` | — | `{seeded: 10}` (chỉ dev, idempotent) |
| GET | `/health` | — | `{status: "UP"}` |

## PU2 — Cart (`http://192.168.137.184:8082`)

| Method | Path | Body | Response |
|---|---|---|---|
| POST | `/cart/add` | `{userId, productId, quantity}` | `{userId, items: {productId: qty}}` |
| GET | `/cart?userId=u1` | — | `{userId, items: {productId: qty}}` |
| DELETE | `/cart/{userId}` | — | `{deleted: true}` |
| GET | `/health` | — | `{status: "UP"}` |

## PU3 — Order (`http://192.168.137.20:8083`)

| Method | Path | Body | Response |
|---|---|---|---|
| POST | `/checkout` | `{userId}` | `{orderId, userId, items, total, createdAt}` |
| GET | `/orders/{userId}` | — | `[{orderId, items, total, createdAt}]` |
| GET | `/health` | — | `{status: "UP"}` |

Lỗi PU3:
- `409 OUT_OF_STOCK` — ít nhất 1 item hết hàng (không tạo order, đã rollback).
- `400 EMPTY_CART` — cart rỗng.

## PU4 — Inventory (`http://192.168.137.98:8084`)

| Method | Path | Body | Response |
|---|---|---|---|
| GET | `/stock/{productId}` | — | `{productId, stock}` |
| POST | `/stock/decrement/{productId}?qty=N` | — | `{productId, remaining}` hoặc `409 OUT_OF_STOCK` |
| POST | `/stock/set/{productId}?qty=N` | — | `{productId, stock}` (chỉ dev) |
| GET | `/health` | — | `{status: "UP"}` |

## CORS

Tất cả PU expose CORS cho:
- `http://192.168.137.41:3000` (Trường)
- `http://localhost:3000` (dev local)

Method: `GET, POST, DELETE, OPTIONS`. Header: `Content-Type, Accept`.
