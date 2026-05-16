# Naming Conventions

## REST endpoints

Đã chốt trong `context/api.md`. Không thêm endpoint mới mà chưa cập nhật vào đó.

- Resource số nhiều cho list: `/products`, `/orders`.
- Resource số ít cho detail: `/products/{id}`.
- Action verb đặt sau resource: `/cart/add`, `/stock/decrement/{id}`.
- Query param dùng camelCase: `?userId=u1`, `?qty=2`.

## Redis keys

Đã chốt trong `context/data-grid.md`. Cấu trúc: `<entity>:<id>` hoặc `<entity>:<sub>:<id>`.

- ✅ `cart:u1`, `stock:p001`, `order:user:u1`.
- ❌ `cart_u1` (underscore), `Cart:u1` (mixed case).

## Java identifier

- Class: `PascalCase` — `ProductController`, `CartService`.
- Method: `camelCase` — `getProducts()`, `addToCart()`.
- Constant: `UPPER_SNAKE` — `private static final String CART_KEY_PREFIX = "cart:"`.
- Bean: `camelCase` — `redisTemplate`, `cartService`.

## TypeScript / React

- Component: `PascalCase` — `ProductTile`, `Navbar`.
- Hook: `useXxx` — `usePollStock`, `useCart`.
- Type/interface: `IXxx` cho domain, `XxxProps` cho props — `IProduct`, `ProductTileProps`.
