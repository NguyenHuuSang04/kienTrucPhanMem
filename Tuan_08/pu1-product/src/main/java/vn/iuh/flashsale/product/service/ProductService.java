package vn.iuh.flashsale.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.iuh.flashsale.product.model.Product;
import vn.iuh.flashsale.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository repository;
  private final RedisTemplate<String, Object> redisTemplate;

  public List<Product> listAll() {
    return repository.findAll();
  }

  public Optional<Product> getById(String id) {
    return repository.findById(id);
  }

  public int seedIfEmpty() {
    if (repository.size() > 0) {
      log.info("Seed skipped — products already present ({} items)", repository.size());
      return 0;
    }
    return forceSeed();
  }

  public int forceSeed() {
    List<Product> seed = SeedData.tenProducts();
    repository.saveAll(seed);
    seed.forEach(p -> redisTemplate.opsForValue().set("stock:" + p.getId(), 100));
    log.info("Seeded {} products and stock", seed.size());
    return seed.size();
  }
}
