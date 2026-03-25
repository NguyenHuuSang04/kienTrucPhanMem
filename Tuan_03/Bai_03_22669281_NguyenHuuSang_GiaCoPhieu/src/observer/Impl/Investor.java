package observer.Impl;

import observer.Observer;

/**
 * @Dự án: Bai_03_22669281_NguyenHuuSang_GiaCoPhieu
 * @Class: Investor
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Investor implements Observer {
    private String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("=> Thông báo cho " + name + ": Cổ phiếu " + stockSymbol + " hiện có giá " + price);
    }
}