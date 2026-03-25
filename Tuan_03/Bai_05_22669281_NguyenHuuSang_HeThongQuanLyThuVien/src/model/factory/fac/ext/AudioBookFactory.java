package model.factory.fac.ext;

import model.factory.Book;
import model.factory.Impl.AudioBook;
import model.factory.fac.BookFactory;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: AudioBookFactory
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class AudioBookFactory extends BookFactory {
    @Override
    public Book createBook(String title, String author, String genre) {
        return new AudioBook(title,author,genre);
    }
}