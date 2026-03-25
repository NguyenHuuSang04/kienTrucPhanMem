package observer.Impl;

import observer.TaskObserver;
import observer.TaskSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Dự án: Bai_04_22669281_NguyenHuuSang_Task_Observer_2
 * @Class: TeamMember
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class TeamMember implements TaskObserver {
    private String name;

    public TeamMember(String name) { this.name = name; }

    @Override
    public void update(String taskName, String status) {
        System.out.println("=> Thông báo cho " + name + ": Công việc '" + taskName + "' hiện tại là " + status);
    }
}