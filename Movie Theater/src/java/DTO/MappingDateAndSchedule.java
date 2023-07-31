/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;
import java.util.List;
import model.Show;

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class MappingDateAndSchedule {
    private Date date;
    private List<Show> shedules;

    public MappingDateAndSchedule() {
    }

    public MappingDateAndSchedule(Date date, List<Show> shedules) {
        this.date = date;
        this.shedules = shedules;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Show> getShedules() {
        return shedules;
    }

    public void setShedules(List<Show> shedules) {
        this.shedules = shedules;
    }
    
    
    
    
}
