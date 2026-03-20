package edu.iuh.fit;

/**
 * @Dự án: 22669281_NguyenHuuSang_DesignPattern_Singleton
 * @Class: CompanyInfo
 * @Tạo vào ngày: 3/14/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class CompanyInfo {
    // 1. Tạo một biến static duy nhất để chứa thực thể của class
    private static CompanyInfo instance;

    private String companyName;

    // 2. Quan trọng: Để private constructor để ngăn việc dùng "new" từ bên ngoài
    private CompanyInfo() {
        this.companyName = "Industrial University of Ho Chi Minh City (IUH)";
    }

    // 3. Phương thức public static để lấy thực thể duy nhất này
    public static CompanyInfo getInstance() {
        if (instance == null) {
            instance = new CompanyInfo();
        }
        return instance;
    }

    public void display() {
        System.out.println("Welcome to: " + companyName);
    }
}