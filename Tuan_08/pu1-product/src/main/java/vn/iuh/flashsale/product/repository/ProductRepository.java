package vn.iuh.flashsale.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vn.iuh.flashsale.product.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

  private static final String KEY = "products:all";

  private final RedisTemplate<String, Object> redisTemplate;

  @SuppressWarnings("unchecked")
  public List<Product> findAll() {
    return redisTemplate.opsForHash().values(KEY).stream()
        .map(o -> (Product) o)
        .toList();
  }

  public Optional<Product> findById(String id) {
    Object value = redisTemplate.opsForHash().get(KEY, id);
    return Optional.ofNullable((Product) value);
  }

  public void save(Product p) {
    redisTemplate.opsForHash().put(KEY, p.getId(), p);
  }

  public void saveAll(List<Product> items) {
    items.forEach(this::save);
  }

  public long size() {
    return redisTemplate.opsForHash().size(KEY);
  }
}
