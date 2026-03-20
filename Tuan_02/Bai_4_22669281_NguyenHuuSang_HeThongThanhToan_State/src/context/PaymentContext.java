package context;

import state.PaymentState;
import state.Impl.NewPaymentState;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_State
 * @Class: PaymentContext
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class PaymentContext {
    private PaymentState state;

    public PaymentContext() {
        this.state = new NewPaymentState();// mặc định là dùng credit
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public void process() {
        System.out.println("Phương thức hiện tại: " + state.getStatusName());
        state.handlePayment(this);
    }
}