package model.strategy;

import model.factory.Book;

import java.util.List;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: SearchStrategy
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public interface SearchStrategy {
    void search (String query, List<Book> books);
}