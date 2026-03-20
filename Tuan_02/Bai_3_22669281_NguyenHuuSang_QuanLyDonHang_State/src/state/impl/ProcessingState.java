package state.impl;

import context.OrderContext;
import state.OrderState;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: state.impl.ProcessingState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
// Trạng thái: Đang xử lý
public class ProcessingState implements OrderState {
    @Override
    public void handleAction(OrderContext context) {
        System.out.println("[Hành vi]: Đang đóng gói sản phẩm và liên hệ vận chuyển");
        context.setState(new ShippedState());;
    }

    @Override
    public String getStatus() {
        return "Đang xử lý";
    }
}