# Conventions (chốt cứng)

Mọi convention dưới đây đã được team đồng thuận. Đổi → cần ADR mới (`memory/decisions.md`).

## Port

| PU | Port |
|---|---|
| PU1 Product | 8081 |
| PU2 Cart | 8082 |
| PU3 Order | 8083 |
| PU4 Inventory | 8084 |
| Frontend dev | 3000 |
| Redis | 6379 |

## Java package

`vn.iuh.flashsale.<pu>` — `<pu>` ∈ `{product, cart, order, inventory}`.

## Maven groupId / artifactId

- `groupId`: `vn.iuh.flashsale`
- `artifactId`: `pu1-product`, `pu2-cart`, `pu3-order`, `pu4-inventory`.

## productId convention

`p001` → `p010` (10 sản phẩm seed). Định dạng `^p\d{3}$`.

## userId

Phase MVP hardcode `"u1"`. Không có auth.

## orderId

Sinh bằng Redis `INCR seq:order` → format `o{:06d}` (vd: `o000001`).

## Redis key prefix

| Prefix | Loại |
|---|---|
| `products:all` | Hash all products |
| `stock:` | String integer |
| `cart:` | Hash |
| `order:` | Hash |
| `order:user:` | List |
| `seq:` | String integer (sequence) |

## Branch name

`feat/<pu>-<feature>`, `fix/<pu>-<bug>`. Vd: `feat/product-list`, `fix/order-out-of-stock`.

## Commit message

`[PU<N>] <verb> <object>`. Vd: `[PU1] add /products endpoint`, `[PU3] fix race condition in checkout`.
