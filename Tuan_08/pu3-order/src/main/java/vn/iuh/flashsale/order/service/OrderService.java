package vn.iuh.flashsale.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vn.iuh.flashsale.order.client.InventoryClient;
import vn.iuh.flashsale.order.dto.OrderResponse;
import vn.iuh.flashsale.order.repository.OrderRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  private final OrderRepository repository;
  private final InventoryClient inventoryClient;

  public OrderResponse checkout(String userId) {
    Map<String, Integer> cart = repository.readCart(userId);
    if (cart.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "EMPTY_CART");
    }

    List<Map.Entry<String, Integer>> processed = new ArrayList<>();
    long total = 0;
    try {
      for (var entry : cart.entrySet()) {
        inventoryClient.decrementOrThrow(entry.getKey(), entry.getValue());
        processed.add(entry);
        long price = repository.getProductPrice(entry.getKey()).orElse(0L);
        total += price * entry.getValue();
      }
    } catch (RuntimeException e) {
      log.warn("Checkout failed mid-flight for user {}: {}", userId, e.getMessage());
      // Rollback các item đã decrement thành công.
      for (var done : processed) {
        try {
          inventoryClient.decrementOrThrow(done.getKey(), -done.getValue());
        } catch (Exception rollbackErr) {
          log.error("Rollback failed for {}: {}", done.getKey(), rollbackErr.getMessage());
        }
      }
      throw e;
    }

    String orderId = repository.nextOrderId();
    String createdAt = Instant.now().toString();
    repository.saveOrder(orderId, userId, cart, total, createdAt);
    repository.deleteCart(userId);

    log.info("Order {} created for user {} total={}", orderId, userId, total);
    return new OrderResponse(orderId, userId, cart, total, createdAt);
  }

  public List<Map<String, String>> listMyOrders(String userId) {
    return repository.ordersByUser(userId);
  }
}
