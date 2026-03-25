package model.factory.fac;

import model.factory.Book;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: BookFactory
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public abstract class BookFactory {
    public abstract Book createBook(String title, String author, String genre);
}