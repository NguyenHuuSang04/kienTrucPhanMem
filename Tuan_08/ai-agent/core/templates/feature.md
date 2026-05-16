# Feature Template

Khi thêm 1 endpoint mới, copy và điền:

```
## Feature: <tên>

- **PU sở hữu**: PU? (Product/Cart/Order/Inventory)
- **Member**: <ai code>
- **Endpoint**: METHOD /path
- **Body**: { ... } hoặc —
- **Response success (200)**: { ... }
- **Response lỗi**: 4xx/5xx + body
- **Redis keys động đến**:
  - GET: ...
  - WRITE: ...
- **Tác động cross-PU**: PU nào sẽ gọi endpoint này?
- **Verify command**:
  curl ...
- **Done criteria**:
  - [ ] Endpoint trả đúng response.
  - [ ] Redis schema không lệch context/data-grid.md.
  - [ ] CORS test từ FE pass.
  - [ ] Đã cập nhật context/api.md.
```
