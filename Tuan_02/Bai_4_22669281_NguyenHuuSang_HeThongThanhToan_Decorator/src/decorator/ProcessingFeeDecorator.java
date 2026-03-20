package decorator;

import model.Payment;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Decorator
 * @Class: ProcessingFeeDecorator
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class ProcessingFeeDecorator extends PaymentDecorator{
    public ProcessingFeeDecorator(Payment decoratedPayment) {
        super(decoratedPayment);
    }

    @Override
    public double getCost() {
        return super.getCost() *1.02;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Phí xử lý 2%";
    }
}