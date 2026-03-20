package context;

import state.OrderState;
import state.impl.NewOrderState;
import state.impl.ProcessingState;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_State
 * @Class: context.OrderContext
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class OrderContext {
    private OrderState state;

    public OrderContext() {
        this.state = new NewOrderState();// mặc định khi tạo là trạng thái mới
    }


    public void setState(OrderState state) {
        this.state = state;
    }

    public  void apply() {
        System.out.println("Trạng thái hiện tại: " + state.getStatus());
        state.handleAction(this);
    }
}