package com.bbd.bursary.manager.model;

public class BBDAdmin {

    private long BBDAdminId;
    private String firstName;
    private String lastName;
    private int isActive;
    private String email;
    private String role;

    public long getBBDAdminId() {
        return BBDAdminId;
    }

    public void setBBDAdminId(long BBDAdminId) {
        this.BBDAdminId = BBDAdminId;
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

    public boolean getIsActive() {
        return isActive == 1;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive ? 1 : 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
