package context;

import strategy.PaymentStrategy;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Strategy
 * @Class: PaymentContext
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class PaymentContext {
    private PaymentStrategy strategy;

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executePayment(int amount) {
        if(strategy == null) {
            System.out.println("Vui lòng chọn phương thức thanh toán");
            return;
        }
        strategy.pay(amount);
    }
}