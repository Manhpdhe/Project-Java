/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class Seat {
    private int seatId;
    private int rowNo;
    private int colNo;
    private int realColNo;
    private int realRowNo;
    private SeatType seatType;
    private Room room;

    public Seat() {
    }

    public int getRealRowNo() {
        return realRowNo;
    }

    public void setRealRowNo(int realRowNo) {
        this.realRowNo = realRowNo;
    }
    
    

    public Seat(int seatId, int rowNo, int colNo, int realColNo, int realRowNo, SeatType seatType, Room room) {
        this.seatId = seatId;
        this.rowNo = rowNo;
        this.colNo = colNo;
        this.realColNo = realColNo;
        this.realRowNo = realRowNo;
        this.seatType = seatType;
        this.room = room;
    }
    
    
    
    public Seat(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getColNo() {
        return colNo;
    }

    public void setColNo(int colNo) {
        this.colNo = colNo;
    }

    public int getRealColNo() {
        return realColNo;
    }

    public void setRealColNo(int realColNo) {
        this.realColNo = realColNo;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    
    
    
}
