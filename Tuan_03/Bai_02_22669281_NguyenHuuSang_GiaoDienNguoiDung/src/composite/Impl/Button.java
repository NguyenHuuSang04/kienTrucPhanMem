package composite.Impl;

import composite.UIComponent;

/**
 * @Dự án: Bai_01_22669281_NguyenHuuSang_GiaoDienNguoiDung
 * @Class: Button
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Button implements UIComponent {
    private String label;

    public Button(String label) {
        this.label = label;
    }

    @Override
    public void render() {
        System.out.println("[Button: " + label + "]");
    }
}