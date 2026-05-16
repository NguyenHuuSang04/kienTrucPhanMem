package vn.iuh.flashsale.order.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {

  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper mapper = new ObjectMapper();

  public Map<String, Integer> readCart(String userId) {
    Map<Object, Object> raw = redisTemplate.opsForHash().entries("cart:" + userId);
    Map<String, Integer> result = new HashMap<>();
    raw.forEach((k, v) -> result.put(String.valueOf(k), Integer.parseInt(String.valueOf(v))));
    return result;
  }

  public void deleteCart(String userId) {
    redisTemplate.delete("cart:" + userId);
  }

  public Optional<Long> getProductPrice(String productId) {
    Object raw = redisTemplate.opsForHash().get("products:all", productId);
    if (raw == null) return Optional.empty();
    try {
      Map<String, Object> tree = mapper.readValue(String.valueOf(raw),
          new TypeReference<>() {});
      Object price = tree.get("price");
      if (price == null) return Optional.empty();
      return Optional.of(Long.parseLong(String.valueOf(price)));
    } catch (JsonProcessingException e) {
      log.warn("Cannot parse product {} JSON: {}", productId, e.getMessage());
      return Optional.empty();
    }
  }

  public String nextOrderId() {
    Long seq = redisTemplate.opsForValue().increment("seq:order");
    return String.format("o%06d", Objects.requireNonNullElse(seq, 1L));
  }

  @SneakyThrows
  public void saveOrder(String orderId, String userId, Map<String, Integer> items,
                        long total, String createdAt) {
    String key = "order:" + orderId;
    Map<String, String> hash = new HashMap<>();
    hash.put("orderId", orderId);
    hash.put("userId", userId);
    hash.put("items", mapper.writeValueAsString(items));
    hash.put("total", String.valueOf(total));
    hash.put("createdAt", createdAt);
    redisTemplate.opsForHash().putAll(key, hash);
    redisTemplate.opsForList().leftPush("order:user:" + userId, orderId);
  }

  @SneakyThrows
  public List<Map<String, String>> ordersByUser(String userId) {
    List<String> ids = redisTemplate.opsForList().range("order:user:" + userId, 0, -1);
    if (ids == null || ids.isEmpty()) return List.of();
    return ids.stream()
        .map(id -> redisTemplate.opsForHash().entries("order:" + id))
        .map(m -> {
          Map<String, String> r = new HashMap<>();
          m.forEach((k, v) -> r.put(String.valueOf(k), String.valueOf(v)));
          return r;
        })
        .toList();
  }
}
