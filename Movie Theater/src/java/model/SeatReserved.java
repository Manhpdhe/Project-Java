/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class SeatReserved {
    private int seatReservedId;
    private Ticket ticket;
    private Show show;
    private boolean releaseStatus;

    public SeatReserved() {
    }

    public SeatReserved(int seatReservedId, Ticket ticket, Show show, boolean releaseStatus) {
        this.seatReservedId = seatReservedId;
        this.ticket = ticket;
        this.show = show;
        this.releaseStatus = releaseStatus;
    }

    public int getSeatReservedId() {
        return seatReservedId;
    }

    public void setSeatReservedId(int seatReservedId) {
        this.seatReservedId = seatReservedId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public boolean isReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(boolean releaseStatus) {
        this.releaseStatus = releaseStatus;
    }
    
    
}
