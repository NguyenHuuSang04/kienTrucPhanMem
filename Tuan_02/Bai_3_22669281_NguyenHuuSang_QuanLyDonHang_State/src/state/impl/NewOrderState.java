package state.impl;

import context.OrderContext;
import state.OrderState;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: state.impl.NewOrderState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
// Trạng thái: mới tạo
public class NewOrderState implements OrderState {
    @Override
    public void handleAction(OrderContext context) {
        System.out.println("=> [Hành vi]: Kiểm tra thông tin đơn hàng và xác nhận thanh toán");
        context.setState(new ProcessingState());// chuyển sang bước tiếp theo
    }

    @Override
    public String getStatus() {
        return "Mới tạo";
    }
}