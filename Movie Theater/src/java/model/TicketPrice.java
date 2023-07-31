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
public class TicketPrice {
    private int ticketPriceId;
    private SeatType seatType;
    private long price;
    private Timestamp createTime;

    public TicketPrice() {
    }

    public TicketPrice(int ticketPriceId, SeatType seatType, long price, Timestamp createTime) {
        this.ticketPriceId = ticketPriceId;
        this.seatType = seatType;
        this.price = price;
        this.createTime = createTime;
    }

    public int getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(int ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    
    
    
}
