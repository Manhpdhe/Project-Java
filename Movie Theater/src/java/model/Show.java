/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Time;

/**
 *
 * @author HP
 */
public class Show {

    private int showID;
    private int startDelay;
    private boolean status;
    private Movie movie;
    private TimeSlot timeSlot;
    

    public Show() {
    }

    public Show(int showID, int startDelay, boolean status, Movie movie, TimeSlot timeSlot) {
        this.showID = showID;
        this.startDelay = startDelay;
        this.status = status;
        this.movie = movie;
        this.timeSlot = timeSlot;
    }

    

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

   

  
}
