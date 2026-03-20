package state.impl;

import context.OrderContext;
import state.OrderState;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: state.impl.ShippedState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */

//Trạng thái: đã giao
public class ShippedState implements OrderState {
    @Override
    public void handleAction(OrderContext context) {
        System.out.println("[Hành vi]: Cập nhật trạng thái 'Giao hàng thành công'");
    }

    @Override
    public String getStatus() {
        return "Đã giao";
    }
}