package state.Impl;

import context.PaymentContext;
import state.PaymentState;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_State
 * @Class: ProcessingPaymentState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class ProcessingPaymentState implements PaymentState {
    @Override
    public void handlePayment(PaymentContext context) {
        System.out.println("=> [Hành vi]: Đang gửi yêu cầu xác thực đến Ngân hàng/PayPal...");
        // Mô phỏng thành công
        context.setState(new CompletePaymentState());
    }

    @Override
    public String getStatusName() {
        return "Đang xử lý";
    }
}