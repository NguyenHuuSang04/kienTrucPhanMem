package strategy.Impl;

import strategy.OrderProcessingStrategy;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_Strategy
 * @Class: RefundStrategy
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class RefundStrategy implements OrderProcessingStrategy {
    @Override
    public void process() {
        System.out.println("[Strategy]: Thực hiện trả hàng và hoàn tiền");
    }
}