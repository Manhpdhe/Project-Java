/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class LanguageRole {
    private int languageRoleId;
    private String languageRoleName;

    public LanguageRole() {
    }

    public LanguageRole(int languageRoleId, String languageRoleName) {
        this.languageRoleId = languageRoleId;
        this.languageRoleName = languageRoleName;
    }
    
    

    public int getLanguageRoleId() {
        return languageRoleId;
    }

    public void setLanguageRoleId(int languageRoleId) {
        this.languageRoleId = languageRoleId;
    }

    public String getLanguageRoleName() {
        return languageRoleName;
    }

    public void setLanguageRoleName(String languageRoleName) {
        this.languageRoleName = languageRoleName;
    }
    
    
}
