---
name: flashsale-sba
description: Flash Sale System sử dụng Space-Based Architecture. Apply when user asks về Flash Sale, Processing Unit, Redis Data Grid, hoặc hỏi cách chạy/build trong repo này. Trỏ về `ai-agent/` cho mọi quyết định kiến trúc.
---

# Flash Sale SBA Skill

Project Chương 7 — Kiến trúc Space-Based. 5 thành viên chạy 5 service trên LAN 192.168.137.x:

| Service | IP:Port | Member |
|---|---|---|
| PU1 Product | 192.168.137.157:8081 | Huy |
| PU2 Cart | 192.168.137.184:8082 | Hiền |
| PU3 Order | 192.168.137.20:8083 | Thái |
| PU4 Inventory + Redis | 192.168.137.98:8084 / 6379 | Sang |
| Frontend | 192.168.137.41:3000 | Trường |

## Bắt buộc đọc trước khi code

1. `ai-agent/README.md` — index toàn bộ tài liệu.
2. `ai-agent/context/architecture.md` — sơ đồ + luồng checkout.
3. `ai-agent/context/api.md` — 6 endpoint chính.
4. `ai-agent/context/data-grid.md` — Redis schema.
5. `ai-agent/core/rules/sba.md` — quy tắc bất biến (không DB, atomic DECR, stateless PU).

## Workflow

- Thêm endpoint → `ai-agent/workflows/feature-flow.md`.
- Fix bug → `ai-agent/workflows/bugfix-flow.md`.
- Chạy local → `ai-agent/workflows/run-local.md`.
- Demo → `ai-agent/workflows/demo-flow.md`.

## Tech stack

Spring Boot 3.3 + Java 21 + Maven, React 19 + Vite + TS + Tailwind v4, Redis 7, Docker Compose.
