package vn.iuh.flashsale.inventory.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InventoryRepository {

  private final RedisTemplate<String, String> redisTemplate;

  private String key(String productId) {
    return "stock:" + productId;
  }

  public Long get(String productId) {
    String v = redisTemplate.opsForValue().get(key(productId));
    return v == null ? null : Long.parseLong(v);
  }

  public Long decrementBy(String productId, int qty) {
    return redisTemplate.opsForValue().decrement(key(productId), qty);
  }

  public Long incrementBy(String productId, int qty) {
    return redisTemplate.opsForValue().increment(key(productId), qty);
  }

  public void set(String productId, int qty) {
    redisTemplate.opsForValue().set(key(productId), String.valueOf(qty));
  }
}
