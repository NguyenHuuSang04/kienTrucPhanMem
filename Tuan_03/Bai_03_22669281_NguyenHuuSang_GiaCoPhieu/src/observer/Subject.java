package observer;

/**
 * @Dự án: Bai_03_22669281_NguyenHuuSang_GiaCoPhieu
 * @Interface: Subject
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public interface Subject {
    void register(Observer o);
    void unregister(Observer o);
    void noitifyObservers();
}