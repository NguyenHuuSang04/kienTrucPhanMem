package decorator;

import model.Payment;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Decorator
 * @Class: DiscountDecorator
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class DiscountDecorator extends PaymentDecorator{
    public DiscountDecorator(Payment decoratedPayment) {
        super(decoratedPayment);
    }

    @Override
    public double getCost() {
        return super.getCost() - 50000;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " - Mã giảm giá 50k";
    }
}