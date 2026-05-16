package vn.iuh.flashsale.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.iuh.flashsale.inventory.service.InventoryService;

import java.util.Map;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService service;

  @GetMapping("/{productId}")
  public Map<String, Object> get(@PathVariable String productId) {
    return Map.of("productId", productId, "stock", service.get(productId));
  }

  @PostMapping("/decrement/{productId}")
  public Map<String, Object> decrement(
      @PathVariable String productId,
      @RequestParam(defaultValue = "1") int qty) {
    long remaining = service.decrementOrThrow(productId, qty);
    return Map.of("productId", productId, "remaining", remaining);
  }

  @PostMapping("/set/{productId}")
  public Map<String, Object> set(
      @PathVariable String productId,
      @RequestParam int qty) {
    service.set(productId, qty);
    return Map.of("productId", productId, "stock", qty);
  }
}
