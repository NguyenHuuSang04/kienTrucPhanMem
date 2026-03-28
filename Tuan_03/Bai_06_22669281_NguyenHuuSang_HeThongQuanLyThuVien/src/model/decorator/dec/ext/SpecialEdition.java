package model.decorator.dec.ext;

import model.decorator.Loan;
import model.decorator.dec.LoanDecorator;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: SpecialEdition
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public class SpecialEdition extends LoanDecorator {

    public SpecialEdition(Loan decoratedLoan) {
        super(decoratedLoan);
    }

    public String getDetails() {
        return super.getDetails() + " [ + Phiên bản có bản dịch đặc biệt ]";
    }


    public double getCost() {
        return super.getCost() + 15000; // Thêm 15k phí bản đặc biệt
    }
}