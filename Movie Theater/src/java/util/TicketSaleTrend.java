/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.DecimalFormat;

/**
 *
 * @author CaoThuLuDau
 */
public class TicketSaleTrend {
    //TTS = Total Tiket Sale
    public static String ticketSaleTrend (Integer currentTTS, Integer previousTTS) {
        String trend = "Can not figured";
        double deltaRatio = ((double) currentTTS / (double) previousTTS);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedDeltaRatio = decimalFormat.format(Math.abs((1 - deltaRatio) * 100));
       
        if (deltaRatio < 1) {
            trend = " Decrease " + formattedDeltaRatio + "% compare to past 7 days";
        } 
        else if (deltaRatio == 1) {
            trend = "Unchange compare to past 7 days";
        }
        else {
            trend = "Increase " + formattedDeltaRatio + "% compare to past 7 days";
        }
        return trend;
    }
}
