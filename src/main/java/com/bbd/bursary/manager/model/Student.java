package com.bbd.bursary.manager.model;


public class Student {
    private long StudentID;
    private String firstName;
    private String lastName;
    private int identityDocument;
    private String phoneNumber;
    private String email;
    private String race;
    private int HeadofdepartmentId;
    private String Motivation;
    private int AverageMarks;
    private int BursaryAmount;
    private  String University;
    private String Status;

    public long getStudentID() {
        return StudentID;
    }

    public void setStudentID(long studentID) {
        StudentID = studentID;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }



    // Default constructor
    public Student() {
    }

    public long getStudentId() {
        return StudentID;
    }

    public void setStudentId(long studentId) {
        this.StudentID = studentId;
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

    public int getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(int identityDocument) {
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
        return HeadofdepartmentId;
    }

    public void setHeadofdepartmentId(int headofdepartmentId) {
        HeadofdepartmentId = headofdepartmentId;
    }

    public String getMotivation() {
        return Motivation;
    }

    public void setMotivation(String motivation) {
        Motivation = motivation;
    }

    public int getAverageMarks() {
        return AverageMarks;
    }

    public void setAverageMarks(int averageMarks) {
        AverageMarks = averageMarks;
    }

    public int getBursaryAmount() {
        return BursaryAmount;
    }

    public void setBursaryAmount(int bursaryAmount) {
        BursaryAmount = bursaryAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getHeadOfDepartmentID() {
        return  HeadofdepartmentId;
    }
}

