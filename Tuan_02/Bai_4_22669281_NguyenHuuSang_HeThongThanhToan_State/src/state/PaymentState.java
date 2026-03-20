package state;

import context.PaymentContext;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_State
 * @Interface: PaymentState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public interface PaymentState {
    void handlePayment (PaymentContext context);

    String getStatusName();
}