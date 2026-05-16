package vn.iuh.flashsale.product.service;

import vn.iuh.flashsale.product.model.Product;

import java.util.List;

final class SeedData {
  private SeedData() {}

  static List<Product> tenProducts() {
    return List.of(
        new Product("p001", "iPhone 16 Pro Max", 32_990_000L,
            "https://images.unsplash.com/photo-1696446702183-be0eb4f1fbb6?w=800",
            "Flash Sale - giảm 5 triệu, số lượng có hạn."),
        new Product("p002", "MacBook Air M3", 27_990_000L,
            "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=800",
            "Air mỏng nhẹ, M3 mạnh mẽ."),
        new Product("p003", "AirPods Pro 2", 5_490_000L,
            "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?w=800",
            "Chống ồn chủ động, USB-C."),
        new Product("p004", "iPad Pro 11", 22_990_000L,
            "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=800",
            "Màn hình Liquid Retina XDR."),
        new Product("p005", "Apple Watch Ultra 2", 19_990_000L,
            "https://images.unsplash.com/photo-1546868871-7041f2a55e12?w=800",
            "Bền bỉ, GPS chính xác."),
        new Product("p006", "Sony WH-1000XM5", 7_990_000L,
            "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=800",
            "Tai nghe chống ồn flagship."),
        new Product("p007", "Samsung Galaxy S25", 24_990_000L,
            "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=800",
            "AI native, camera 200MP."),
        new Product("p008", "Logitech MX Master 3S", 2_290_000L,
            "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=800",
            "Chuột năng suất cao cấp."),
        new Product("p009", "Dell UltraSharp U2725QE", 17_990_000L,
            "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=800",
            "Màn hình 4K, USB-C 90W."),
        new Product("p010", "Keychron K2 Pro", 2_790_000L,
            "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=800",
            "Bàn phím cơ, Bluetooth.")
    );
  }
}
