package com.bbd.bursary.manager.model;

import java.time.LocalDate;

public class BursaryFund {

    private long bursaryFundId;
    private double fundAmount;
    private double fundRemainingAmount;
    private double studentMaxAllocation;
    private LocalDate financialDate;
    private long bbdAdminId;

    public long getBursaryFundId() {
        return bursaryFundId;
    }

    public void setBursaryFundId(long bursaryFundId) {
        this.bursaryFundId = bursaryFundId;
    }

    public double getFundAmount() {
        return fundAmount;
    }

    public void setFundAmount(double fundAmount) {
        this.fundAmount = fundAmount;
    }

    public double getFundRemainingAmount() {
        return fundRemainingAmount;
    }

    public void setFundRemainingAmount(double fundRemainingAmount) {
        this.fundRemainingAmount = fundRemainingAmount;
    }

    public double getStudentMaxAllocation() {
        return studentMaxAllocation;
    }

    public void setStudentMaxAllocation(double studentMaxAllocation) {
        this.studentMaxAllocation = studentMaxAllocation;
    }

    public LocalDate getFinancialDate() {
        return financialDate;
    }

    public void setFinancialDate(LocalDate financialDate) {
        this.financialDate = financialDate;
    }

    public long getBbdAdminId() {
        return bbdAdminId;
    }

    public void setBbdAdminId(long bbdAdminId) {
        this.bbdAdminId = bbdAdminId;
    }
}
