# user-service (:8081)

Quản lý đăng ký, đăng nhập, hồ sơ user. Phát event `USER_REGISTERED`.

## Endpoint

| Method | Path        | Auth   | Mô tả                                     |
| ------ | ----------- | ------ | ----------------------------------------- |
| POST   | `/register` | Public | Tạo user mới, publish `USER_REGISTERED`   |
| POST   | `/login`    | Public | Trả về JWT (HS256, TTL 1h)                |
| GET    | `/me`       | Bearer | Lấy thông tin user hiện tại               |

Khi gọi qua **api-gateway** thì path là `/api/users/register`,
`/api/users/login`, `/api/users/me`.

## Event publish

| Event              | Routing key       | Payload                                  |
| ------------------ | ----------------- | ---------------------------------------- |
| `USER_REGISTERED`  | `user.registered` | `{ userId, username, email }`            |

Wrap trong `EventEnvelope { eventId, eventType, occurredAt, producer, payload }`.

## Env

| Biến                 | Default                                   |
| -------------------- | ----------------------------------------- |
| `DB_HOST/PORT`       | `localhost:5432`                          |
| `DB_USERNAME`        | `movieticket`                             |
| `DB_PASSWORD`        | `movieticket`                             |
| `RABBITMQ_HOST`      | `localhost`                               |
| `JWT_SECRET`         | (>= 32 chars; **bắt buộc giống các service khác**) |
| `JWT_EXPIRES_IN_SECONDS` | `3600`                                |

## Run local

```bash
./mvnw spring-boot:run
```

## Test

```bash
./mvnw test
```

## Seed data

Khi service start lần đầu, `AdminSeeder` tự tạo:
- `username=admin / password=Admin@123 / role=ADMIN,USER`

## Polyrepo note

Vì project chia polyrepo (mỗi service folder riêng), POJO event được **copy**
giữa các service. Nếu cần thêm field, sửa đồng bộ ở:
`user-service`, `notification-service` (consumer của `USER_REGISTERED`).
