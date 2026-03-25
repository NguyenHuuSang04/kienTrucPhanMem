package model.strategy.Impl;

import model.factory.Book;
import model.strategy.SearchStrategy;

import java.util.List;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: SearchByAuthor
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class SearchByAuthor implements SearchStrategy {
    @Override
    public void search(String query, List<Book> books) {
        System.out.println("--- Kết quả tìm kiếm theo TÁC GIẢ: '" + query + "' ---");
        for (Book b : books) {
            if (b.getAuthor().toLowerCase().contains(query.toLowerCase())) b.displayInfo();
        }
    }
}