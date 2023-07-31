/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class Room {
    private int roomID;
    private String roomName;
    private int numRows;
    private int numCols;
    private Branch branch;

    public Room() {
    }
    
    

    public Room(int roomID, String roomName, int numRows, int numCols, Branch branch) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.numRows = numRows;
        this.numCols = numCols;
        this.branch = branch;
    }

    public Room(int roomID) {
        this.roomID = roomID;
    }
   

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    
}
