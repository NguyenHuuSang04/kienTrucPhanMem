package observer.Impl;

import observer.Observer;
import observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Dự án: Bai_03_22669281_NguyenHuuSang_GiaCoPhieu
 * @Class: Stock
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Stock implements Subject {
    private String symbol;
    private double price;
    private List<Observer> observers = new ArrayList<>();

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
        System.out.println("\n[Hệ thống]: Giá cổ phiếu " + symbol + " thay đổi thành: " + price);
        noitifyObservers(); // tự động thông báo khi giá đổi
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void noitifyObservers() {
        for(Observer o: observers) {
            o.update(symbol, price); // gửi thông tin đến từng người

        }
    }
}