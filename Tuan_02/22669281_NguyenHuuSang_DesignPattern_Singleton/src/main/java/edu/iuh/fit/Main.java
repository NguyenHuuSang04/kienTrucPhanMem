package edu.iuh.fit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    // Không thể dùng: CompanyInfo info = new CompanyInfo(); (Sẽ báo lỗi)

        // Lấy thực thể duy nhất thông qua getInstance()
        CompanyInfo info1 = CompanyInfo.getInstance();
        info1.display();

        // Thử lấy thêm một thực thể nữa
        CompanyInfo info2 = CompanyInfo.getInstance();

        // Kiểm tra xem info1 và info2 có phải là một không
        if (info1 == info2) {
            System.out.println("=> info1 và info2 là cùng một thực thể (Singleton hoạt động!)");
        }
    }
}

