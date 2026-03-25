package model.decorator.dec;

import model.decorator.Loan;

/**
 * @Dự án: Bai_05_22669281_NguyenHuuSang_HeThongQuanLyThuVien
 * @Class: LoanDecorator
 * @Tạo vào ngày: 3/25/2026
 * @Tác giả: Nguyen Huu Sang
 */
public abstract class LoanDecorator implements Loan {
    protected Loan decoratedLoan;

    public LoanDecorator(Loan decoratedLoan) {
        this.decoratedLoan = decoratedLoan;
    }

    public String getDetails() {
        return decoratedLoan.getDetails();
    }


    public double getCost() {
        return decoratedLoan.getCost();
    }
}