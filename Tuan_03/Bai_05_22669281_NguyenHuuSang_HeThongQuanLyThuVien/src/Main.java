import model.decorator.Impl.BasicLoan;
import model.decorator.Loan;
import model.decorator.dec.ext.ExtendedDuration;
import model.decorator.dec.ext.SpecialEdition;
import model.factory.Book;
import model.factory.Impl.Ebook;
import model.factory.Impl.PaperBook;
import model.factory.fac.BookFactory;
import model.factory.fac.ext.EBookFactory;
import model.factory.fac.ext.PaperBookFactory;
import model.observer.Impl.Member;
import model.observer.LibraryObserver;
import model.singleton.Library;
import model.strategy.Impl.SeachByName;
import model.strategy.Impl.SearchByAuthor;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== HỆ THỐNG QUẢN LÝ THƯ VIỆN IUH ==========\n");

        // 1. SINGLETON: Lấy thực thể Thư viện duy nhất
        Library iuhLib = Library.getInstance();

        // 2. OBSERVER: Thành viên đăng ký nhận tin
        Member sang = new Member("Nguyễn Hữu Sang");
        iuhLib.registerObserver(sang);

        // 3. FACTORY METHOD: Thủ thư nhập sách mới (Tự động notify cho Sang)
        BookFactory paperFactory = new PaperBookFactory();
        BookFactory eBookFactory = new EBookFactory();

        Book book1 = paperFactory.createBook("Design Patterns", "Gang of Four", "Công nghệ");
        iuhLib.addBook(book1); // Thông báo sẽ bắn ra ở đây

        Book book2 = eBookFactory.createBook("Clean Code", "Robert C. Martin", "Công nghệ");
        iuhLib.addBook(book2);

        // 4. STRATEGY: Sang thực hiện tìm kiếm sách
        System.out.println("\n--- Sang thực hiện tìm kiếm sách ---");
        iuhLib.setSearchStrategy(new SeachByName());
        iuhLib.performSearch("Design");

        iuhLib.setSearchStrategy(new SearchByAuthor());
        iuhLib.performSearch("Robert");

        // 5. DECORATOR: Sang bắt đầu mượn sách và thêm tính năng (Theo sơ đồ)
        // Thành viên 'initiates' Loan
        Loan sangLoan = sang.initiateLoan(book1);

        // Thêm các option (Decorate)
        System.out.println("... Sang yêu cầu gia hạn và lấy bản dịch đặc biệt ...");
        sangLoan = new ExtendedDuration(sangLoan); // Gia hạn
        sangLoan = new SpecialEdition(sangLoan);   // Bản đặc biệt

        // Kết quả cuối cùng
        System.out.println("\n--- CHI TIẾT GIAO DỊCH MƯỢN SÁCH ---");
        System.out.println("Nội dung: " + sangLoan.getDetails());
        System.out.println("Tổng chi phí dịch vụ: " + sangLoan.getCost() + " VND");

        System.out.println("\n===================================================");
    }


}