/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class FormatType {
    private int formatTypeId;
    private String formatName;

    public FormatType() {
    }

    public FormatType(int formatTypeId, String formatName) {
        this.formatTypeId = formatTypeId;
        this.formatName = formatName;
    }

    public int getFormatTypeId() {
        return formatTypeId;
    }

    public void setFormatTypeId(int formatTypeId) {
        this.formatTypeId = formatTypeId;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }
    
    
}
