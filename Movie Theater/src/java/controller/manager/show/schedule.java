/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager.show;

import dal.dao.BranchDBContext;
import dal.dao.MovieDBContext;
import dal.dao.RoomDBContext;
import dal.dao.TimeSlotDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Branch;
import model.Movie;
import model.Room;
import model.Show;
import model.TimeSlot;

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class schedule extends HttpServlet {

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    private static Date findNextDay(Date date) {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }

    private static Map<Integer, String> mappingDate(Date monday, Date sunday) {
        Map<Integer, String> mapDate = new HashMap<>();
        Date temp = monday;
        Integer date = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        while (temp.before(sunday) || temp.equals(sunday)) {
            String formattedDate = dateFormat.format(temp);
            mapDate.put(date, formattedDate);
            date += 1;
            temp = findNextDay(temp);
        }
//        mapDate.get(1).
        return mapDate;
    }

    private static Map<Integer, String> currentDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // ????
        Date monday = c.getTime();
        c.add(Calendar.DAY_OF_YEAR, 6); // ????
        Date sunday = c.getTime();
        Map<Integer, String> mapDate = mappingDate(monday, sunday);
        return mapDate;
    }

    private static List<Show> processDisplaySchedule(List<Show> shows) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");
        SimpleDateFormat simpleHourFormat = new SimpleDateFormat("H");
        List<Show> schedules = new ArrayList<>();
        for (Show show : shows) {
            // Change java.sql.TimeStamp to Date in String
            String date
                    = simpleDateFormat.format(show.getTimeSlot()
                            .getStartTime());
            // Change java.sql.TimeStamp to Hour in String
            // Initialize start time and end time for each day
            TimeSlot timeSlot = new TimeSlot(show.getTimeSlot().getTimeSlotId(),
                    show.getTimeSlot().getStartTime(), show.getTimeSlot().getEndTime(), show.getTimeSlot().getRoom());
            Show showDTO = new Show(show.getShowID(), show.getStartDelay(),
                    show.isStatus(), show.getMovie(), timeSlot);
            schedules.add(show);
        }
        return schedules;
    }

    private static void defaultValue(HttpServletRequest request) {
        BranchDBContext branchDBContext = new BranchDBContext();
        RoomDBContext roomDBContext = new RoomDBContext();
        MovieDBContext moviesDBContext = new MovieDBContext();
        TimeSlotDBContext scheduleDBContext = new TimeSlotDBContext();

        String branchId = request.getParameter("cinema-name");
        String roomId = request.getParameter("room-id");
        String dates = request.getParameter("week");
        if (dates != null) {
            try {
                Map<Integer, String> mapDate = new HashMap<>();
                String[] date = dates.split("To");
                String startDate = date[0].trim() + "/2023";
                String endDate = date[1].trim() + "/2023";
                try {
                    Date monday = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                    Date sunday = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                    mapDate = mappingDate(monday, sunday);
                } catch (ParseException ex) {
                    Logger.getLogger(schedule.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("startDate", date[0]);
                request.setAttribute("dates", mapDate);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                Map<Integer, String> mapDate = currentDate();
                request.setAttribute("dates", mapDate);
            }
        } else {
            Map<Integer, String> mapDate = currentDate();
            request.setAttribute("dates", mapDate);
        }
        if (branchId == null) {
            branchId = "1";
            if (roomId == null || roomId.equals("")) {
                roomId = "1";
            }
        } else if (roomId == null) {
            List<Room> rooms = roomDBContext.getByBranchID(branchId).getReturnObject();
            roomId = rooms.get(0).getRoomID() + "";
        }

        List<Show> shows = scheduleDBContext.findShowByRoomID(roomId).getReturnObject();
        List<Branch> branches = branchDBContext.all().getReturnObject();
        List<Room> rooms = roomDBContext.getByBranchID(branchId).getReturnObject();
        List<Movie> movies = moviesDBContext.all().getReturnObject();

        request.setAttribute("selectedDate", dates);
        request.setAttribute("shows", processDisplaySchedule(shows));
        request.setAttribute("selectedBranch", branchId);
        request.setAttribute("roomId", roomId);
        request.setAttribute("branches", branches);
        request.setAttribute("rooms", rooms);
        request.setAttribute("movies", movies);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            defaultValue(request);
            request.getRequestDispatcher("/views/manager/schedule/schedule.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BranchDBContext branchDBContext = new BranchDBContext();
        RoomDBContext roomDBContext = new RoomDBContext();
        MovieDBContext moviesDBContext = new MovieDBContext();
        TimeSlotDBContext scheduleDBContext = new TimeSlotDBContext();
        String action = request.getParameter("action");
        if (action
                == null || action.equals(
                        "")) {
            String dates = request.getParameter("week");
            Map<Integer, String> mapDate = new HashMap<>();
            String[] date = dates.split("To");
            String startDate = date[0].trim() + "/2023";
            String endDate = date[1].trim() + "/2023";
            try {
                Date monday = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                Date sunday = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                mapDate = mappingDate(monday, sunday);
            } catch (ParseException ex) {
                Logger.getLogger(schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            defaultValue(request);
            request.setAttribute("startDate", date[0]);
            request.setAttribute("dates", mapDate);
            request.getRequestDispatcher("/views/manager/schedule/schedule.jsp").forward(request, response);
        } else if (action.equals(
                "create-schedule")) {
            String dates = request.getParameter("week");
            String branchID = request.getParameter("cinema-name");
            String roomID = request.getParameter("room-id");
            String[] dateTime = request.getParameterValues("date-time");
            String[] startTime = request.getParameterValues("start-time");
            String[] endTime = request.getParameterValues("end-time");
            String[] state = request.getParameterValues("state");
            String[] deleteID = request.getParameterValues("delete-id");
            String[] slotID = request.getParameterValues("slot-id");
            String[] delay = request.getParameterValues("delay");
            // Find Room through ID
            Room room = new Room();
            try {
                room = roomDBContext.getRoomById(Integer.parseInt(roomID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Declare to format String to DATE
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy H");
            // Initialize inputDate (String) to parse in format dd/MM H
            String openTimeString;
            String endTimeString;
            // parsedDate is casted fron String inputDate to Date
            Date openTimeParse;
            Date endTimeParse;
            // Declare TIMESTAMP variable to be casted from DATE
            Timestamp openTimeStamp;
            Timestamp endTimeStamp;
            // Declare SCHEDULE
            TimeSlot slot;
            // Delete TimeSlot
            try {
                if (slotID != null || slotID.length > 0) {
                    for (String s : slotID) {
                        try {
                            scheduleDBContext.delete(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            // Delete show 
            try {
                if (deleteID.length > 0 || deleteID != null) {
                    for (String id : deleteID) {
                        Show show = scheduleDBContext.findShowByID(id);
                        try {
                            scheduleDBContext.delete(show);
                        } catch (SQLException ex) {
                            Logger.getLogger(schedule.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            // Create Slot
            for (int i = 0; i < startTime.length; i++) {
                try {
                    openTimeString = dateTime[i] + " " + startTime[i];
                    endTimeString = dateTime[i] + " " + endTime[i];
                    openTimeParse = dateFormatter.parse(openTimeString);
                    endTimeParse = dateFormatter.parse(endTimeString);
                    openTimeStamp = new Timestamp(openTimeParse.getTime());
                    endTimeStamp = new Timestamp(endTimeParse.getTime());
                    if (state[i].equalsIgnoreCase("slot")) {
                        slot = new TimeSlot(room, openTimeStamp, endTimeStamp);
                        String msg = scheduleDBContext.insert(slot).getMessage();
                    } else if (state[i].equalsIgnoreCase("update")) {

                    } else {
                        try {
                            slot = new TimeSlot(room, openTimeStamp, endTimeStamp);
                            Movie movie = moviesDBContext.getByTitle(state[i]).getReturnObject();
                            Show show = new Show();
                            show.setStatus(true);
                            show.setTimeSlot(slot);
                            try {
                                if (delay[i] != null) {
                                    show.setStartDelay(Integer.parseInt(delay[i]));
                                } else {
                                    show.setStartDelay(0);
                                }
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();   
                            }
                            show.setMovie(movie);
                            scheduleDBContext.duplicateInsert(show);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(schedule.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(schedule.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect("schedule?cinema-name=" + branchID + "&week=" + dates + "&room-id=" + roomID);
        }
    }

}
