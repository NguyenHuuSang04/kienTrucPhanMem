# Review Code

## Khi dùng
Trước khi merge PR của bất kỳ member nào.

## Checklist (đối chiếu `core/rules/sba.md`)

- [ ] `pom.xml` không có `data-jpa`, `jdbc`, `mysql`, `postgresql`, `h2`.
- [ ] Không `@Entity`, không `JpaRepository`.
- [ ] Stock decrement dùng `decrement()` của RedisTemplate, không `get → set`.
- [ ] CORS đúng origin `http://192.168.137.41:3000` + `http://localhost:3000`.
- [ ] DTO request có `@Valid`.
- [ ] Endpoint mới đã được thêm vào `context/api.md`.
- [ ] Redis key mới đã có trong `context/data-grid.md`.
- [ ] Không hardcode IP của PU khác trong code (dùng env var).
- [ ] Test `curl` pass.
- [ ] Demo flow `workflows/demo-flow.md` pass end-to-end.
