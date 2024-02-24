package com.bbd.bursary.manager.dto;

public class InstituteInfoDTO {

    private long instituteId;
    private String instituteName;
    private long applicationStatusID;
    private long instituteReviewerID;
    private String instituteRepresentativeFirstName;
    private String instituteRepresentativeLastName;
    private String instituteRepresentativePhoneNumber;
    private String instituteRepresentativeEmail;

    public long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(long instituteId) {
        this.instituteId = instituteId;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public long getApplicationStatusID() {
        return applicationStatusID;
    }

    public void setApplicationStatusID(long applicationStatusID) {
        this.applicationStatusID = applicationStatusID;
    }

    public long getInstituteReviewerID() {
        return instituteReviewerID;
    }

    public void setInstituteReviewerID(long instituteReviewerID) {
        this.instituteReviewerID = instituteReviewerID;
    }

    public String getInstituteRepresentativeFirstName() {
        return instituteRepresentativeFirstName;
    }

    public void setInstituteRepresentativeFirstName(String instituteRepresentativeFirstName) {
        this.instituteRepresentativeFirstName = instituteRepresentativeFirstName;
    }

    public String getInstituteRepresentativeLastName() {
        return instituteRepresentativeLastName;
    }

    public void setInstituteRepresentativeLastName(String instituteRepresentativeLastName) {
        this.instituteRepresentativeLastName = instituteRepresentativeLastName;
    }

    public String getInstituteRepresentativePhoneNumber() {
        return instituteRepresentativePhoneNumber;
    }

    public void setInstituteRepresentativePhoneNumber(String instituteRepresentativePhoneNumber) {
        this.instituteRepresentativePhoneNumber = instituteRepresentativePhoneNumber;
    }

    public String getInstituteRepresentativeEmail() {
        return instituteRepresentativeEmail;
    }

    public void setInstituteRepresentativeEmail(String instituteRepresentativeEmail) {
        this.instituteRepresentativeEmail = instituteRepresentativeEmail;
    }
}
