import context.PaymentContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("---Hệ thống thanh toán---");

        //Khởi tọa ngữ cảnh mặc định
        PaymentContext payment = new PaymentContext();

        //B1: Khách hàng nhấn "Thanh toán"
        payment.process();

        //Bước 2: Hệ thống xác thực ( tự động chuyển )
        payment.process();

        //Bước 3: Hoàn tất giao dịch
        payment.process();
    }
}