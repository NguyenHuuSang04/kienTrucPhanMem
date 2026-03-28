package model.factory.fac.ext;

import model.factory.Book;
import model.factory.Impl.PaperBook;
import model.factory.fac.BookFactory;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: PaperBookFactory
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class PaperBookFactory extends BookFactory {

    @Override
    public Book createBook(String title, String author, String genre) {
        return new PaperBook(title,author, genre);
    }
}