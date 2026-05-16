package vn.iuh.flashsale.order.dto;

import java.util.Map;

public record OrderResponse(
    String orderId,
    String userId,
    Map<String, Integer> items,
    long total,
    String createdAt) {}
