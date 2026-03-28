package model.singleton;

import model.factory.Book;
import model.observer.LibraryObserver;
import model.strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: Library
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Library {
    private static Library instance;
    private List<Book> books;
    private SearchStrategy searchStrategy;
    private List<LibraryObserver> observers = new ArrayList<>();

    private Library() {
        books = new ArrayList<>();
        System.out.println("---Khởi tạo thực thể thư viện duy nhất---");
    }

    public static synchronized Library getInstance() {
        if(instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        notifyObservers("Sách mới vừa thêm: " + book.getTitle());
    }

    public void showBooks() {
        System.out.println("\n--- DANH SÁCH SÁCH TRONG THƯ VIỆN ---");
        for (Book book: books) {
            book.displayInfo();
        }
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void performSearch(String query) {
        if(searchStrategy == null) {
            System.out.println("Lỗi: Chưa chọn chiến thuật tìm kiếm!");
            return;
        }
        searchStrategy.search(query, books);
    }

    // Quản lý observer
    public void registerObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for(LibraryObserver observer: observers) {
            observer.update(message);
        }
    }
}