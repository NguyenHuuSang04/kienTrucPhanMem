package composite.Impl;

import composite.UIComponent;

/**
 * @Dự án: Bai_01_22669281_NguyenHuuSang_GiaoDienNguoiDung
 * @Class: TextField
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class TextField implements UIComponent {
    private String placeholder;

    public TextField(String placeholder) {
        this.placeholder = placeholder;
    }



    @Override
    public void render() {
        System.out.println("[TextField: " + placeholder +"]");
    }
}