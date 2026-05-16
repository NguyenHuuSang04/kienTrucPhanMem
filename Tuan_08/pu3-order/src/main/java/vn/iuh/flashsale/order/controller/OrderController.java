package vn.iuh.flashsale.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.iuh.flashsale.order.dto.CheckoutRequest;
import vn.iuh.flashsale.order.dto.OrderResponse;
import vn.iuh.flashsale.order.service.OrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService service;

  @PostMapping("/checkout")
  public OrderResponse checkout(@RequestBody @Valid CheckoutRequest req) {
    return service.checkout(req.userId());
  }

  @GetMapping("/orders/{userId}")
  public List<Map<String, String>> orders(@PathVariable String userId) {
    return service.listMyOrders(userId);
  }

  @GetMapping("/health")
  public Map<String, String> health() {
    return Map.of("status", "UP", "service", "pu3-order");
  }
}
