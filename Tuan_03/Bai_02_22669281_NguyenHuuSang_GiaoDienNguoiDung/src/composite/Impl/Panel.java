package composite.Impl;

import composite.UIComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Dự án: Bai_01_22669281_NguyenHuuSang_GiaoDienNguoiDung
 * @Class: Panel
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Panel implements UIComponent {
    private String name;
    private List<UIComponent> children = new ArrayList<>();

    public Panel(String name) {
        this.name = name;
    }


    public void add(UIComponent component) {
        children.add(component);
    }

    @Override
    public void render() {
        System.out.println("--- Bắt đầu Panel: " + name + " ---");
        for (UIComponent child : children) {
            child.render(); // Đệ quy gọi lệnh render của con
        }
        System.out.println("--- Kết thúc Panel: " + name + " ---");
    }
}