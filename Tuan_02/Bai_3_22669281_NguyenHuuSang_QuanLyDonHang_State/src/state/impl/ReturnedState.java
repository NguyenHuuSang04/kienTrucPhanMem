package state.impl;

import context.OrderContext;
import state.OrderState;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: ReturnedState
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class ReturnedState implements OrderState {
    @Override
    public void handleAction(OrderContext context) {
        System.out.println("[Hành vi]: thực hiện hoàn tiền");
    }

    @Override
    public String getStatus() {
        return "Hoàn tiền";
    }
}