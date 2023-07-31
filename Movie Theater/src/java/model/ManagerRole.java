/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class ManagerRole {
    private int managerRoleId;
    private String roleName;

    public ManagerRole() {
    }

    public ManagerRole(int managerRoleId, String roleName) {
        this.managerRoleId = managerRoleId;
        this.roleName = roleName;
    }

    public int getManagerRoleId() {
        return managerRoleId;
    }

    public void setManagerRoleId(int managerRoleId) {
        this.managerRoleId = managerRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
}
