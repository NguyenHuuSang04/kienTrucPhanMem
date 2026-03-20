package decorator.children;

import decorator.OrderProcessorDecorator;
import model.Order;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_Decorator
 * @Class: ShippedProcessor
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class ShippedProcessor extends OrderProcessorDecorator {
    public ShippedProcessor(Order order) {
        super(order);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("=>[Xử lý]: Cập nhật trạng thái đơn hàng là đã giao");
    }
}