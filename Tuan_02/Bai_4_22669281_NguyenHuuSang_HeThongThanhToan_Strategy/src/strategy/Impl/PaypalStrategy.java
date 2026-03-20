package strategy.Impl;

import strategy.PaymentStrategy;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Strategy
 * @Class: PaypalStrategy
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class PaypalStrategy implements PaymentStrategy {
    private String email;

    public PaypalStrategy(String email) {
        this.email = email;
    }

    @Override
    public void pay(int amount) {
        System.out.println("=> [Strategy]: Đã thanh toán " + amount + " VND qua ví PayPal (" + email + ").");
    }
}