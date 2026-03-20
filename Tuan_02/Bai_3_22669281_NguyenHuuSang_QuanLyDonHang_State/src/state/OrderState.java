package state;

import context.OrderContext;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: state.OrderState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public interface OrderState {
    void handleAction(OrderContext context);

    String getStatus();

}