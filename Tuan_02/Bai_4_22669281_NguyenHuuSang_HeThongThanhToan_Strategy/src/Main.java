import context.PaymentContext;
import strategy.Impl.CreditCardStrategy;
import strategy.Impl.PaypalStrategy;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PaymentContext context = new PaymentContext();
        int totalAmount = 5000000;

        System.out.println("---Thẻ tín dụng---");
        context.setPaymentStrategy(new CreditCardStrategy("NGUYEN HUU SANG", "123"));
        context.executePayment(totalAmount);

        System.out.println("\n--- Khách hàng đổi sang PayPal ---");
        context.setPaymentStrategy(new PaypalStrategy("sang.iuh@gmail.com"));
        context.executePayment(totalAmount);

    }
}