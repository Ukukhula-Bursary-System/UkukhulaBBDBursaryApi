package com.bbd.bursary.manager.dto;

public class InstitutionFundAllocationDTO {

    private double allocatedAmount;
    private double instituteId;

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public double getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(double instituteId) {
        this.instituteId = instituteId;
    }
}
