/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class Manager {
    private int managerId;
    private String accountName;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private Date DOB;
    private String address;
    private String phoneNumber;
    private ManagerRole managerRole;
    private Branch branch;

    public Manager() {
    }

    public Manager(int managerId, String accountName, String firstName, String lastName, Gender gender, String email, Date DOB, String address, String phoneNumber, ManagerRole managerRole, Branch branch) {
        this.managerId = managerId;
        this.accountName = accountName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.DOB = DOB;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.managerRole = managerRole;
        this.branch = branch;
    }

    

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public ManagerRole getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(ManagerRole managerRole) {
        this.managerRole = managerRole;
    }
    
    
}
