import composite.FileSystemItem;
import composite.Impl.File;
import composite.Impl.Folder;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Tạo file
        FileSystemItem file_1 = new File("baitap.java", 10);
        FileSystemItem file_2 = new File("tailieu.pdf", 500);
        FileSystemItem file_3 = new File("hinh_anh.png", 150);

        //tạo thư mục ocn
        Folder subFolder = new Folder("DoAn_MonHoc");
        subFolder.add(file_1);
        subFolder.add(file_2);

        //Tạo thư mục cha ( root )
        Folder root = new Folder("C_Drive");
        root.add(subFolder);
        root.add(file_3);

        //Hiển thị cấu trúc cây
        System.out.println("Cấu trúc cây");
        root.showDetails("");
    }
}