package app;

import context.OrderContext;
import state.impl.CanceledState;
import state.impl.ReturnedState;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    //Tạo đơn hàng: Luồng 1 ( đơn hàng thành công )
        System.out.println("Luồng 1: Đơn hàng thành công");
        OrderContext order_1 = new OrderContext();
        order_1.apply();;// xử lý trạng thái new
        order_1.apply();// xử lý trạng thái processing
        order_1.apply();// xử lý trạng thái shipped


        // Luồng 2: đơn hàng bị hủy
        System.out.println("\n\nLuồng 2: Đơn hàng bị hủy");
        OrderContext order_2 = new OrderContext();
        System.out.println("Khách hàng nhấn hủy đơn");
        order_2.setState(new CanceledState());
        order_2.apply();

        // Luồng 3: đơn hàng hoàn tiền
        System.out.println("\n\nLuồng 3: Đơn hàng hoàn tiền");
        OrderContext order_3 = new OrderContext();
        System.out.println("Khách hàng nhấn hoàn tiền");
        order_3.setState(new ReturnedState());
        order_3.apply();
    }
}