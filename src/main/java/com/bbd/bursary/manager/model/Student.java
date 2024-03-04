package com.bbd.bursary.manager.model;


public class Student {
    private long studentID;
    private String firstName;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private String email;
    private String race;
    private int headOfDepartmentId;
    private String motivation;
    private int averageMarks;
    private int bursaryAmount;
    private String status;
    private  String University;

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
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

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getHeadofdepartmentId() {
        return headOfDepartmentId;
    }

    public void setHeadofdepartmentId(int headofdepartmentId) {
        this.headOfDepartmentId = headofdepartmentId;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public int getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(int averageMarks) {
        this.averageMarks = averageMarks;
    }

    public int getBursaryAmount() {
        return bursaryAmount;
    }

    public void setBursaryAmount(int bursaryAmount) {
        this.bursaryAmount = bursaryAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }
}

