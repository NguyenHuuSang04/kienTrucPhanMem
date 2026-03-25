package model.observer.Impl;

import model.decorator.Impl.BasicLoan;
import model.decorator.Loan;
import model.factory.Book;
import model.observer.LibraryObserver;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: Member
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Member implements LibraryObserver {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("=> [Thông báo gửi đến " + name + "]: " + message);
    }

    //Member mới có quyền mượn sách
    public Loan initiateLoan (Book book) {
        System.out.println("\n[Action]: " + name + " đang thực hiện mượn sách: " + book.getTitle());
        return new BasicLoan(book);
    }
}