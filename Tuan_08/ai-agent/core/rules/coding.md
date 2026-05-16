# Coding Rules

## Java / Spring Boot

- Package: `vn.iuh.flashsale.<pu>` với `<pu>` ∈ `{product, cart, order, inventory}`.
- Layer: `controller` → `service` → `repository` (Repository ở đây = wrapper RedisTemplate, không phải JpaRepository).
- DTO trong `dto/`, Entity trong `model/`. Không trộn `model` với JPA.
- Validation bằng `@Valid` + Bean Validation annotations (`@NotBlank`, `@Min`).
- Trả lỗi qua `@RestControllerAdvice` chung — không `try/catch` trong controller.
- Logging: `@Slf4j` + log INFO cho request vào, ERROR khi exception.
- Không dùng `RestTemplate` (deprecated). Dùng `RestClient` (Spring 6.1+) hoặc `WebClient`.

## React / TypeScript

- Function components + hooks. Không class component.
- File `.tsx` cho component, `.ts` cho logic thuần.
- 1 component / 1 file. Tên file PascalCase trùng tên export.
- API call qua axios instance từ `api/`. **Cấm fetch trực tiếp trong component** — luôn qua hook hoặc handler.
- State: `useState` cho local, không Redux/Zustand cho phase MVP.
- Type-safe: định nghĩa interface ở `types/index.ts`, không dùng `any`.

## Comment

- Mặc định không comment. Code đọc được = không cần comment.
- Comment chỉ cho: workaround quirk Redis, lý do chọn `DECR` thay vì lock, edge case không hiển nhiên.
- Tiếng Anh hoặc tiếng Việt đều được, nhất quán trong 1 file.
