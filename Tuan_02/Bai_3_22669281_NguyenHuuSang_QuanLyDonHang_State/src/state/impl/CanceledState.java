package state.impl;

import context.OrderContext;
import state.OrderState;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: state.impl.CanceledState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
//Trạng thái: hủy
public class CanceledState implements OrderState {
    @Override
    public void handleAction(OrderContext context) {
        System.out.println("[Hành vi]: Hủy đơn hàng");
    }

    @Override
    public String getStatus() {
        return "Hủy";
    }
}