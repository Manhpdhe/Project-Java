/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Schedule {
    private int scheduleId;
    private Room room;
    private Timestamp openTime;
    private Timestamp closeTime;

    public Schedule() {
    }

    public Schedule(int scheduleId) {
        this.scheduleId = scheduleId;
    }
    

    public Schedule(int scheduleId, Room room, Timestamp openTime, Timestamp closeTime) {
        this.scheduleId = scheduleId;
        this.room = room;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
    
    public Schedule(Room room, Timestamp openTime, Timestamp closeTime) {
        this.room = room;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }
    
    
}
