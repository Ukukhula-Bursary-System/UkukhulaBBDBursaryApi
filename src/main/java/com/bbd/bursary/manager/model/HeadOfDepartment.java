package com.bbd.bursary.manager.model;

public class HeadOfDepartment {

    private long instituteId;
    private long headOfDepartmentId;
    private String email;
    private String instituteName;

    public long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(long instituteId) {
        this.instituteId = instituteId;
    }

    public long getHeadOfDepartmentId() {
        return headOfDepartmentId;
    }

    public void setHeadOfDepartmentId(long headOfDepartmentId) {
        this.headOfDepartmentId = headOfDepartmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
}
