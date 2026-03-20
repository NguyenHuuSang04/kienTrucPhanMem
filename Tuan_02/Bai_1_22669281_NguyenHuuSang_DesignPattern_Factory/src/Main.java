//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        NotificationFactory factory = new NotificationFactory();

        // Bạn không cần dùng 'new EmailNotification()'
        Notification n1 = factory.createNotification("EMAIL");
        n1.notifyUser();;

    }
}