/**
 * @Dự án: 22669281_NguyenHuuSang_DesignPattern_Factory
 * @Class: NotificationFactory
 * @Tạo vào ngày: 3/14/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class NotificationFactory {
    // Đây là Factory Method đơn giản
    public Notification createNotification(String type) {
        if (type == null || type.isEmpty()) return null;
        if ("EMAIL".equals(type)) return new EmailNotification();
        if ("SMS".equals(type)) return new SMSNotification();
        return null;
    }
}