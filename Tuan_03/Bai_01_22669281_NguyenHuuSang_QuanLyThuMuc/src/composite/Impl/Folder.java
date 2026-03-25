package composite.Impl;

import composite.FileSystemItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Dự án: Bai_01_22669281_NguyenHuuSang_QuanLyThuMuc
 * @Class: Folder
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> items = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemItem item) {
        items.add(item);
    }

    public void remove(FileSystemItem item) {
        items.remove(item);
    }



    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "+ Folder: [" + name + "]");
        for (FileSystemItem item: items) {
            item.showDetails(indent + "       ");
        }
    }
}