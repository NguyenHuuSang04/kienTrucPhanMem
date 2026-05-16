package vn.iuh.flashsale.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.iuh.flashsale.product.service.ProductService;

import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final ProductService service;

  @PostMapping("/seed")
  public Map<String, Integer> seed() {
    return Map.of("seeded", service.seedIfEmpty());
  }
}
