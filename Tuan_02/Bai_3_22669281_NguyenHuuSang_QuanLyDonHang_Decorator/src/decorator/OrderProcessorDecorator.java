package decorator;

import model.Order;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_Decorator
 * @Class: OrderProcessorDecorator
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class OrderProcessorDecorator implements Order {
    protected Order decoratedOrder;

    public OrderProcessorDecorator(Order order) {
        this.decoratedOrder = order;
    }

    @Override
    public void process() {
        decoratedOrder.process();
    }
}