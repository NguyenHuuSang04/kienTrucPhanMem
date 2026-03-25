package observer;

/**
 * @Dự án: Bai_04_22669281_NguyenHuuSang_Task_Observer_2
 * @Interface: TaskSubject
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public interface TaskSubject {
    void attach(TaskObserver o);
    void detach(TaskObserver o);
    void notifyObservers();
}