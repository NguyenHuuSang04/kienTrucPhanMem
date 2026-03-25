package model.decorator.dec.ext;

import model.decorator.Loan;
import model.decorator.dec.LoanDecorator;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: ExtendedDuration
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class ExtendedDuration extends LoanDecorator {
    public ExtendedDuration(Loan loan) {
        super(loan);
    }

    public String getDetails() {
        return super.getDetails() + " [ + Gia hạn thêm 7 ngày ]";
    }

    public double getCost() {
        return super.getCost() + 5000;
    }
}