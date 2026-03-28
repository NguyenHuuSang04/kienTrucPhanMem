package model.decorator.Impl;

import model.decorator.Loan;
import model.factory.Book;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: BasicLoan
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class BasicLoan implements Loan {
    private Book book;

    public BasicLoan(Book book) {
        this.book = book;
    }

    @Override
    public String getDetails() {
        return "Mượn sách: " + book.getTitle();
    }

    @Override
    public double getCost() {
        return 10000;
    }
}