/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/*
 * @author HP
 */
public class Branch {
    
    private int branchID;
    private String location;
    private String branchName;


    public Branch() {
    }

    public Branch(int branchID, String location, String branchName) {
        this.branchID = branchID;
        this.location = location;
        this.branchName = branchName;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
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

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

   

   
    

    
}
