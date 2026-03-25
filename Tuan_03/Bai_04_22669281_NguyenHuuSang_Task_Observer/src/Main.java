import observer.Impl.Task;
import observer.Impl.TeamMember;
import observer.TaskObserver;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Tạo một Task mới
        Task task = new Task("Thiết kế Database", "Mới tạo");

        // Các thành viên đăng ký theo dõi Task này
        TaskObserver member1 = new TeamMember("Anh Tuấn (Developer)");
        TaskObserver member2 = new TeamMember("Minh Thư (Tester)");

        task.attach(member1);
        task.attach(member2);

        // Giả sử trạng thái task thay đổi
        task.setStatus("Đang xử lý");

        // Hoàn thành công việc
        task.setStatus("Đã hoàn thành");
    }
}