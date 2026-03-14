/**
 * @Dự án: 22669281_NguyenHuuSang_DesignPattern_Factory
 * @Class: SMSNotification
 * @Tạo vào ngày: 3/14/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class SMSNotification implements Notification{
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS notification...");
    }
}