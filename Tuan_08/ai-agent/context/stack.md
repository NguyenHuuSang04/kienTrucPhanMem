# Tech Stack

## Backend (4 PU)

| Mục | Phiên bản | Ghi chú |
|---|---|---|
| Java | 21 (Eclipse Temurin) | LTS |
| Spring Boot | 3.3.4 | Web + Data Redis + Validation |
| Build | Maven (Maven Wrapper `mvnw`) | Không cần cài Maven local |
| Redis client | Lettuce (mặc định) | Không cần Jedis |
| Lombok | 1.18.34 | Chỉ dùng `@Data`, `@RequiredArgsConstructor`, `@Slf4j` |
| JSON | Jackson (đi kèm Spring Boot) | — |

### `pom.xml` skeleton

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>3.3.4</version>
</parent>

<properties>
  <java.version>21</java.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

**Cấm thêm**: `spring-boot-starter-data-jpa`, `mysql-connector-j`, `postgresql`, `h2`, `spring-boot-starter-jdbc`. Vi phạm SBA.

## Frontend

| Mục | Phiên bản |
|---|---|
| Vite | 7 (latest) |
| React | 19 |
| TypeScript | 5.6+ |
| TailwindCSS | 4 (`@tailwindcss/vite` plugin) |
| axios | 1.7+ |
| react-router-dom | 6.x |

## Infra

| Mục | Phiên bản |
|---|---|
| Redis | 7-alpine (Docker) |
| Docker | Desktop 4+ (Windows) |
| Docker Compose | v2 (đi kèm Docker Desktop) |
