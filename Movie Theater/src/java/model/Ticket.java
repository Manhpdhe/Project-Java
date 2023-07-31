/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Ticket {
    private int ticketId;
    private Order order;
    private TicketPrice ticketPrice;
    private Show show;
    private Seat seat;

    public Ticket() {
    }

    public Ticket(int ticketId, Order order, TicketPrice ticketPrice, Show show, Seat seat) {
        this.ticketId = ticketId;
        this.order = order;
        this.ticketPrice = ticketPrice;
        this.show = show;
        this.seat = seat;
    }

    

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    
    

    
    
    
}
