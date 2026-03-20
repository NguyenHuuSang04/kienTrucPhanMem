package decorator.children;

import decorator.OrderProcessorDecorator;
import model.Order;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_Decorator
 * @Class: CheckInforProcessor
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class CheckInforProcessor extends OrderProcessorDecorator {
    public CheckInforProcessor(Order order) {
        super(order);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("=>[Xử lý]: Kiểm tra thông tin đơn hàng");
    }
}