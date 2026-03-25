package observer.Impl;

import observer.TaskObserver;
import observer.TaskSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Dự án: Bai_04_22669281_NguyenHuuSang_Task_Observer_2
 * @Class: Task
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Task implements TaskSubject {
    private String taskName;
    private String status;
    private List<TaskObserver> observers = new ArrayList<>();

    public Task(String taskName, String status) {
        this.taskName = taskName;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
        System.out.println("\n[Hệ thống]: Task '" + taskName + "' chuyển sang trạng thái: " + status);
        notifyObservers();
    }

    @Override
    public void attach(TaskObserver o) { observers.add(o); }

    @Override
    public void detach(TaskObserver o) { observers.remove(o); }

    @Override
    public void notifyObservers() {
        for (TaskObserver o : observers) {
            o.update(taskName, status);
        }
    }
}