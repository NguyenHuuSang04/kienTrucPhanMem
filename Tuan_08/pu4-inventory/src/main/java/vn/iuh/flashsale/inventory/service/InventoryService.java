package vn.iuh.flashsale.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.iuh.flashsale.inventory.repository.InventoryRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

  private final InventoryRepository repository;

  public long get(String productId) {
    Long v = repository.get(productId);
    return v == null ? 0L : v;
  }

  public long decrementOrThrow(String productId, int qty) {
    Long remaining = repository.decrementBy(productId, qty);
    if (remaining == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND");
    }
    if (remaining < 0) {
      repository.incrementBy(productId, qty);
      log.warn("Out of stock: product={} requested={} rolled back", productId, qty);
      throw new ResponseStatusException(HttpStatus.CONFLICT, "OUT_OF_STOCK");
    }
    log.info("Stock decremented: product={} qty={} remaining={}", productId, qty, remaining);
    return remaining;
  }

  public void set(String productId, int qty) {
    repository.set(productId, qty);
  }
}
