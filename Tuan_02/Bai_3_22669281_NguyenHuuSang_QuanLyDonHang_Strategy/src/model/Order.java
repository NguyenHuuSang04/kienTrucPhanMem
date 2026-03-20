package model;

import strategy.OrderProcessingStrategy;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_Strategy
 * @Class: Order
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Order {
    private OrderProcessingStrategy strategy;

    // Cho phép đổi chiến lược xử lý tại runtime

    public void setStrategy(OrderProcessingStrategy strategy) {
        this.strategy = strategy;
    }

    public  void executeProcess() {
        if(strategy != null) {
            strategy.process();
        } else {
            System.out.println("Chưa chọn chiến lược xử lý");
        }
    }
}