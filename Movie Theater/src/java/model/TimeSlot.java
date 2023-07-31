/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author CaoThuLuDau
 */
public class TimeSlot {
    private int timeSlotId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Room room;

    public TimeSlot() {
    }

    public TimeSlot(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public TimeSlot(Room room, Timestamp startTime, Timestamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }
    
    

    public TimeSlot(int timeSlotID, Timestamp startTime, Timestamp endTime, Room room) {
        this.timeSlotId = timeSlotID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotID) {
        this.timeSlotId = timeSlotID;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        return sdf.format(startTime);
    }
    
    public Integer getOpenTimeInteger() {
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        return Integer.parseInt(sdf.format(startTime));
    }
    
    public Integer getCloseTimeInteger() {
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        return Integer.parseInt(sdf.format(endTime));
    }
    
    
}
