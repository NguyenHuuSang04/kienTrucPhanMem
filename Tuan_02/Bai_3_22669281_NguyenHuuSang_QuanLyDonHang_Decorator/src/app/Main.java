package app;

import decorator.children.*;
import model.Impl.BasicOrder;
import model.Order;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Đơn hàng dùng decorator");

        //1. đơn hàng mới: kiểm tra thông tin
        Order newOrder = new CheckInforProcessor(new BasicOrder());
        newOrder.process();

        System.out.println("\n--- Đơn hàng tiến tới khâu đóng gói");
        //2. Bọc thêm lớp đóng vói
        Order processingOrder = new PackingShippingProcessor(newOrder);
        processingOrder.process();

        System.out.println("\n--- Đơn hàng đã giao đến");;
        //3. Bọc thêm lớp đã giao
        Order shippedOrder = new ShippedProcessor(processingOrder);
        shippedOrder.process();

        System.out.println("\n---Đơn hàng bị hoàn trả");
        //4. Bọc lớp hoàn trả
        Order refundOrder = new RefundProcessor(shippedOrder);
        refundOrder.process();

        System.out.println("\n--- Đơn hàng bị hủy");;
        //5. Đơn hàng bị hủy
        Order cancelOrder = new CancelProcessor(processingOrder);
        cancelOrder.process();
    }
}