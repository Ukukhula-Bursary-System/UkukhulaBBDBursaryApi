package com.bbd.bursary.manager.model;

import java.time.LocalDate;

public class InstitutionFundAllocation {

    private long institutionFundAllocationId;
    private double allocatedAmount;
    private double allocatedRemainingAmount;
    private String instituteName;
    private LocalDate financialDate;

    public long getInstitutionFundAllocationId() {
        return institutionFundAllocationId;
    }

    public void setInstitutionFundAllocationId(long institutionFundAllocationId) {
        this.institutionFundAllocationId = institutionFundAllocationId;
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public double getAllocatedRemainingAmount() {
        return allocatedRemainingAmount;
    }

    public void setAllocatedRemainingAmount(double allocatedRemainingAmount) {
        this.allocatedRemainingAmount = allocatedRemainingAmount;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public LocalDate getFinancialDate() {
        return financialDate;
    }

    public void setFinancialDate(LocalDate financialDate) {
        this.financialDate = financialDate;
    }
}
