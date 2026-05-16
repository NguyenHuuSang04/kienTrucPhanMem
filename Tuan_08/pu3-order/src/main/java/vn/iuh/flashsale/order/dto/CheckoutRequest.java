package vn.iuh.flashsale.order.dto;

import jakarta.validation.constraints.NotBlank;

public record CheckoutRequest(@NotBlank String userId) {}
