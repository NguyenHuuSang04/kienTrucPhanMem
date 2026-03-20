import decorator.DiscountDecorator;
import decorator.ProcessingFeeDecorator;
import model.Impl.BasicPayment;
import model.Payment;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //1. Khởi tạo thanh toán gốc 1.00.0000
        Payment payment = new BasicPayment(1000000);

        //2. Khách hàng áp dụng mã giảm giá
        payment = new DiscountDecorator(payment);

        //3. Hệ thống tính thêm phí xử lý giao dịch
        payment = new ProcessingFeeDecorator(payment);

        System.out.println("Nội dung: " + payment.getDescription());
        System.out.println("=> Tổng tiền cuối cùng: " + payment.getCost() + " VND");
    }
}