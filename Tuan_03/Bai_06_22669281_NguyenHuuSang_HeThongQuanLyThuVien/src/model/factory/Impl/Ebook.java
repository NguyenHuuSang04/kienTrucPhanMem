package model.factory.Impl;

import model.factory.Book;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: Ebook
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Ebook implements Book {
    private String title;
    private String author;
    private String genre;


    public Ebook(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public void displayInfo() {
        System.out.println("[Ebook]: " + title);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getGenre() {
        return genre;
    }
}