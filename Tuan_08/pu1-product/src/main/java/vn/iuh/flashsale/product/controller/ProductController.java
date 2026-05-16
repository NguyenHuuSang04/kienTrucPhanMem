package vn.iuh.flashsale.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vn.iuh.flashsale.product.model.Product;
import vn.iuh.flashsale.product.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService service;

  @GetMapping("/products")
  public List<Product> listProducts() {
    return service.listAll();
  }

  @GetMapping("/products/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable String id) {
    return service.getById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/health")
  public Map<String, String> health() {
    return Map.of("status", "UP", "service", "pu1-product");
  }
}
