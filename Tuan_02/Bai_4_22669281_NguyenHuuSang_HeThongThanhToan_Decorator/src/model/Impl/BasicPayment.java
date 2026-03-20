package model.Impl;

import model.Payment;

/**
 * @Dự án: Bai_4_22669281_NguyenHuuSang_HeThongThanhToan_Decorator
 * @Class: BasicPayment
 * @Tạo vào ngày: 3/20/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class BasicPayment implements Payment {
    private double amount;

    public BasicPayment(double amount) {
        this.amount = amount;
    }

    @Override
    public double getCost() {
        return amount;
    }

    @Override
    public String getDescription() {
        return "Thanh toán gốc";
    }
}