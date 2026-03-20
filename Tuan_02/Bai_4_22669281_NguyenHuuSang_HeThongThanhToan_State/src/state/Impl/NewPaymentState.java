package state.Impl;

import context.PaymentContext;
import state.PaymentState;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_State
 * @Class: NewPaymentState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class NewPaymentState implements PaymentState {
    @Override
    public void handlePayment(PaymentContext context) {
        System.out.println("=> [Hành vi]: Đang hiển thị cổng thanh toán. Chờ xác thực...");
        context.setState(new ProcessingPaymentState()); // tự động chuyển sang trạng thái
    }

    @Override
    public String getStatusName() {
        return "Chờ thanh toán";
    }
}