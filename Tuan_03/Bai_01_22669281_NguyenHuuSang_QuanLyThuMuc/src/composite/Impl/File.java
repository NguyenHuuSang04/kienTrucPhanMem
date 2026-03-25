package composite.Impl;

import composite.FileSystemItem;

/**
 * @Dự án: Bai_01_22669281_NguyenHuuSang_QuanLyThuMuc
 * @Class: File
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class File implements FileSystemItem {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void showDetails(String indent) {
        System.out.println(indent + "- File: " + name + " (" + size + "KB)");
    }
}