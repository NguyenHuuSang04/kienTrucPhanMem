package state.Impl;

import context.PaymentContext;
import state.PaymentState;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_State
 * @Class: CompletePaymentState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class CompletePaymentState implements PaymentState {
    @Override
    public void handlePayment(PaymentContext context) {
        System.out.println("=> [Hành vi]: Giao dịch thành công! Đang xuất hóa đơn điện tử.");
    }

    @Override
    public String getStatusName() {
        return "Thành công";
    }
}