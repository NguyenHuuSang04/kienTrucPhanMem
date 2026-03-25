package model.factory.fac.ext;

import model.factory.Book;
import model.factory.Impl.Ebook;
import model.factory.fac.BookFactory;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: EBookFactory
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class EBookFactory extends BookFactory {

    @Override
    public Book createBook(String title, String author, String genre) {
        return new Ebook(title,author,genre);
    }
}