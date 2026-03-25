import adapter.Impl.XmlToJsonAdapter;
import adapter.JsonService;
import adapter.XmlSystem;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //1. Khởi tạo hệ thống cũ ( adaptee )
        XmlSystem oldSystem = new XmlSystem();

        //2. Tạo adapter để chuyển đổi xml --> json
        JsonService adapter = new XmlToJsonAdapter(oldSystem);

        //3. Client gủi dữ liệu json thông qua adapter
        String modernData = "{\"user\":\"Sang\", \"status\":\"Active\"}";

        System.out.println("---Ứng dụng gửi dữ liệu json---");
        adapter.sendData(modernData);
    }
}