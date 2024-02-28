package com.bbd.bursary.manager.model;

public class BursaryApplicants {
    private long bursaryApplicantId;
    private double bursaryAmount;
    private int averageMarks;
    private String motivation;
    private String status;
    private long  headOfDepartmentId;
    private String firstName;
    private String lastName;
    private String email;
    private long institutionFundAllocationId;
    private long BBDAdminId;

    public long getBursaryApplicantId() {
        return bursaryApplicantId;
    }

    public void setBursaryApplicantId(long bursaryApplicantId) {
        this.bursaryApplicantId = bursaryApplicantId;
    }

    public double getBursaryAmount() {
        return bursaryAmount;
    }

    public void setBursaryAmount(double bursaryAmount) {
        this.bursaryAmount = bursaryAmount;
    }

    public int getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(int averageMarks) {
        this.averageMarks = averageMarks;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getHeadOfDepartmentId() {
        return headOfDepartmentId;
    }

    public void setHeadOfDepartmentId(long headOfDepartmentId) {
        this.headOfDepartmentId = headOfDepartmentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getInstitutionFundAllocationId() {
        return institutionFundAllocationId;
    }

    public void setInstitutionFundAllocationId(long institutionFundAllocationId) {
        this.institutionFundAllocationId = institutionFundAllocationId;
    }

    public long getBBDAdminId() {
        return BBDAdminId;
    }

    public void setBBDAdminId(long BBDAdminId) {
        this.BBDAdminId = BBDAdminId;
    }
}
