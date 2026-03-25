import composite.Impl.Button;
import composite.Impl.Panel;
import composite.Impl.TextField;
import composite.UIComponent;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Tạo cái component lẻ
        UIComponent btnLogin = new Button("Đăng nhập");
        UIComponent txtUser = new TextField("Tên đăng nhập");
        UIComponent txtPass = new TextField("Mật khẩu");

        //Gom nhóm vào 1 panel ( hộp thoại đăng nhập )
        Panel loginDialog = new Panel("Hộp thoại đăng nhập");
        loginDialog.add(txtUser);
        loginDialog.add(txtPass);
        loginDialog.add(btnLogin);

        //tạo toolbar
        Panel toolbar = new Panel("Thanh công cụ");
        toolbar.add(new Button("Home"));
        toolbar.add(new Button("Settings"));


        // Gom tất cả vào một Cửa sổ chính (Main Window)
        Panel mainWindow = new Panel("Cửa sổ ứng dụng");
        mainWindow.add(toolbar);
        mainWindow.add(loginDialog);

        // gọi render từ gốc
        mainWindow.render();




    }
}