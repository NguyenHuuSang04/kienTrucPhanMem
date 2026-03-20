package strategy.Impl;

import strategy.PaymentStrategy;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Strategy
 * @Class: CreditCardStrategy
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class CreditCardStrategy implements PaymentStrategy {
    private String name;
    private String cardNumber;

    public CreditCardStrategy(String name, String cardNumber) {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("=>[Stategy]: Đã thanh toán" + amount + " VND bằng thẻ tín dụng tên: " + name);
    }
}