# Architecture Decision Records (ADR)

## ADR-001: Chọn Lettuce thay Jedis

- **Date**: 2026-05-09
- **Status**: Accepted
- **Context**: Spring Boot 3 mặc định đi kèm Lettuce. Jedis là sync, Lettuce async non-blocking.
- **Decision**: Dùng Lettuce (mặc định, không cần khai báo).
- **Consequences**: Hiệu năng tốt cho 1000+ req/s. Không cần config gì thêm.

## ADR-002: Redis chạy ở máy Sang (PU4 Inventory)

- **Date**: 2026-05-09
- **Status**: Accepted
- **Context**: Cần 1 Redis dùng chung. PU4 ghi stock nhiều nhất (mỗi checkout đều DECR).
- **Decision**: Sang host Redis trong cùng `docker-compose.yml` với PU4. IP `192.168.137.98:6379`.
- **Consequences**: PU4 → Redis qua localhost (nhanh nhất). PU1/PU2/PU3 qua mạng LAN (1-2ms).

## ADR-003: 4 Spring Boot project rời thay vì multi-module

- **Date**: 2026-05-09
- **Status**: Accepted
- **Context**: 5 thành viên 5 máy, mỗi người chỉ làm 1 PU.
- **Decision**: Mỗi PU là 1 project Maven độc lập (`pu1-product/`, ...). Không multi-module.
- **Consequences**: Mỗi member chỉ cần build folder của mình. Không xung đột `pom.xml`. Trade-off: code DTO bị duplicate giữa các PU (chấp nhận được, đúng tinh thần SBA).

## ADR-004: PU3 đọc cart trực tiếp từ Redis

- **Date**: 2026-05-09
- **Status**: Accepted
- **Context**: PU3 checkout cần data cart. 2 lựa chọn: gọi PU2 qua REST hoặc đọc Redis trực tiếp.
- **Decision**: Đọc Redis trực tiếp. PU2 là owner ghi, PU3 là consumer đọc.
- **Consequences**: Giảm 1 hop mạng. Giữ đúng tinh thần SBA. Sau checkout PU3 xóa cart để tránh xung đột.

## ADR-005: Vite + Tailwind v4 + TS cho FE

- **Date**: 2026-05-09
- **Status**: Accepted
- **Decision**: Vite + React 19 + TS + Tailwind v4 (CSS-first @theme). Không Shadcn.

## ADR-006: Không có authentication ở phase MVP

- **Date**: 2026-05-09
- **Status**: Accepted
- **Decision**: Hardcode `userId = "u1"` ở FE và truyền qua request body/query.
