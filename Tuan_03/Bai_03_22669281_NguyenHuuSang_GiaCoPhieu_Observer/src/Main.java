import observer.Impl.Investor;
import observer.Impl.Stock;
import observer.Observer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Tạo đối tượng subject ( cổ phiếu )
        Stock appleStock = new Stock("AAPL", 150.0);

        //tạo các obervers ( nhà đầu tư )
        Observer sang = new Investor("Sang");
        Observer nguyen = new Investor("Nguyen");

        //Đăng ký theo dõi
        appleStock.register(sang);
        appleStock.register(nguyen);

        //Giả sử cổ phiếu thay đổi
        appleStock.setPrice(200);

        // Một người hủy đăng ký
        appleStock.unregister(nguyen);

        //Giá lại thay đổi
        appleStock.setPrice(500);



    }
}