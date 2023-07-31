/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import model.Customer;

/**
 *
 * @author Admin
 */
public class TicketHistory {
    private int ticketHistoryId;
    private String orderCode;
    private String seatsString;
    private String timeString;
    private Timestamp orderTime;
    private Customer customer;
    private String roomName;
    private String branchName;
    private String movieName;
    private long totalPrice;
    private int paymentStatus; 
    private String paymentLink;

    public TicketHistory() {
    }

    public TicketHistory(int ticketHistoryId, String orderCode, String seatsString, String timeString, Timestamp orderTime, Customer customer, String roomName, String branchName, String movieName, long totalPrice, int paymentStatus, String paymentLink) {
        this.ticketHistoryId = ticketHistoryId;
        this.orderCode = orderCode;
        this.seatsString = seatsString;
        this.timeString = timeString;
        this.orderTime = orderTime;
        this.customer = customer;
        this.roomName = roomName;
        this.branchName = branchName;
        this.movieName = movieName;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.paymentLink = paymentLink;
    }

    

    

    

    

    public int getTicketHistoryId() {
        return ticketHistoryId;
    }

    public void setTicketHistoryId(int ticketHistoryId) {
        this.ticketHistoryId = ticketHistoryId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    

    public String getSeatsString() {
        return seatsString;
    }

    public void setSeatsString(String seatsString) {
        this.seatsString = seatsString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
    

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    
    
    
    
    
}
