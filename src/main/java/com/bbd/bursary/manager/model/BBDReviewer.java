package com.bbd.bursary.manager.model;

public class BBDReviewer {
    private double bursaryAmount;
    private String status;
    private String firstName;
    private String lastName;
    private String instituteName;
    private long BBDAdminID;
    private long studentID;

    public BBDReviewer () {

    }
    public double getBursaryAmount() {
        return bursaryAmount;
    }

    public void setBursaryAmount(double bursaryAmount) {
        this.bursaryAmount = bursaryAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public long getBBDAdminID() {
        return BBDAdminID;
    }

    public void setBBDAdminID(long BBDAdminID) {
        this.BBDAdminID = BBDAdminID;
    }
}
