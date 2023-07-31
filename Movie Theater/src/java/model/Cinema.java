/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class Cinema {
    private Integer branchID;
    private String location;
    private String branchName;

    public Cinema() {
    }

    public Cinema(Integer brachID, String location, String brachName) {
        this.branchID = brachID;
        this.location = location;
        this.branchName = brachName;
    }

    public Cinema(String location, String brachName) {
        this.location = location;
        this.branchName = brachName;
    }
    
    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer brachID) {
        this.branchID = brachID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String brachName) {
        this.branchName = brachName;
    }
    
    
}
