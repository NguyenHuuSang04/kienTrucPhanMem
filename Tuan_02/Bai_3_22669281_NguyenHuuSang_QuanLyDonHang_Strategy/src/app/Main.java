package app;

import model.Order;
import strategy.Impl.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        System.out.println("---Giai đoạn 1: đơn hàng mới ---");
        order.setStrategy(new NewOrderStrategy());;
        order.executeProcess();

        System.out.println("---Giai đoạn 2: đóng gói---");
        order.setStrategy(new ProcessingStrategy());
        order.executeProcess();

        System.out.println("---Giai đoạn 3: Đã giao hàng---");
        order.setStrategy(new DeliveredStrategy());
        order.executeProcess();


        System.out.println("---Giai đoạn 4: Hoàn trả---");
        order.setStrategy(new RefundStrategy());
        order.executeProcess();


        System.out.println("---Giai đoạn 2.1: Hủy hàng");
        order.setStrategy(new CancelStrategy());
        order.executeProcess();

    }
}