package decorator;

import model.Payment;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Decorator
 * @Class: PaymentDecorator
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class PaymentDecorator implements Payment {
    protected Payment decoratedPayment;

    public PaymentDecorator(Payment decoratedPayment) {
        this.decoratedPayment = decoratedPayment;
    }

    @Override
    public double getCost() {
        return decoratedPayment.getCost();
    }

    @Override
    public String getDescription() {
        return decoratedPayment.getDescription();
    }
}