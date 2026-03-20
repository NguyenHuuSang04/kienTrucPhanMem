package model.Impl;

import model.Order;

/**
 * @Dự án: Bai_3_22669281_NguyenHuuSang_QuanLyDonHang_Decorator
 * @Class: BasicOrder
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class BasicOrder implements Order {
    @Override
    public void process() {
        System.out.println("Đơn hàng gốc: Khởi tạo thông tin");
    }
}