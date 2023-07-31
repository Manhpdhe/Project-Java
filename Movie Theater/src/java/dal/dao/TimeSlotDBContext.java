/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movie;
import model.Response;
import model.Room;
import model.TimeSlot;
import model.Show;
import model.Show;
import model.TimeSlot;

/**
 *
 * @author DUONG THANH LUAN
 */
public class TimeSlotDBContext extends DBContext<TimeSlot> {
    // Insert to slot

    private static final RoomDBContext roomDBContext = new RoomDBContext();
    private static final MovieDBContext moviesDBContext = new MovieDBContext();

    @Override
    public Response<Map<String, Boolean>> insert(TimeSlot model) {
        Map<String, Boolean> result = new HashMap<>();
        try ( PreparedStatement pstm = connection.prepareCall("INSERT INTO TimeSlot VALUES (?,?,?)")) {
            pstm.setTimestamp(1, model.getStartTime());
            pstm.setTimestamp(2, model.getEndTime());
            pstm.setInt(3, model.getRoom().getRoomID());
            pstm.executeUpdate();
            result.put("Insert TimeSlot Successfull", true);
            return new Response<>(Response.OK, result, "Insert successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "Fail");
    }

    public Response<Map<String, Boolean>> insert(Show model) {
        Map<String, Boolean> result = new HashMap<>();
//        insert(model.getTimeSlot());
        try ( PreparedStatement pstm = connection.prepareCall("INSERT INTO Show VALUES (?,?,?,?)")) {
            pstm.setInt(1, model.getStartDelay());
            pstm.setBoolean(2, model.isStatus());
            pstm.setInt(3, model.getMovie().getMid());
            pstm.setInt(4, model.getTimeSlot().getTimeSlotId());
            pstm.executeUpdate();
            result.put("Insert Show Successfull", true);
            return new Response<>(Response.OK, result, "Insert Show successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "Fail to insert show");
    }

    @Override
    public Response<TimeSlot> get(TimeSlot model) {
        TimeSlot schedule = new TimeSlot();
        try ( PreparedStatement pstm = connection.
                prepareCall("Select * from TimeSlot where TimeSlotID = ?")) {
            pstm.setInt(1, model.getTimeSlotId());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                schedule = new TimeSlot(rs.getInt("TimeSlotID"), rs.getTimestamp("StartTime"), rs.getTimestamp("EndTime"), roomDBContext.get(new Room(rs.getInt("roomID"))).getReturnObject());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response<>(Response.OK, schedule, "Get TimeSlot Successfully!");
    }

    @Override
    public Response<Map<String, Boolean>> delete(TimeSlot model) {
        Map<String, Boolean> result = new HashMap<>();
//        insert(model.getTimeSlot());

        try ( PreparedStatement pstm = connection.prepareCall("DELETE FROM TimeSlot WHERE TimeSlotId = ?")) {
            pstm.setInt(1, model.getTimeSlotId());
            pstm.executeUpdate();
            result.put("Delete Show Successfull", true);
            return new Response<>(Response.OK, result, "Delete Show successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "Fail to insert show");
    }

    public Boolean delete(Show show) throws SQLException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        try ( PreparedStatement pstm = connection.
                prepareCall("DELETE FROM Show WHERE ShowID = ?")) {
            pstm.setInt(1, show.getShowID());
            pstm.executeUpdate();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try ( PreparedStatement pstm = connection.prepareCall("DELETE FROM TimeSlot WHERE TimeSlotId = ?")) {
            pstm.setInt(1, show.getTimeSlot().getTimeSlotId());
            pstm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.commit();

        }
        return false;
    }

    public Boolean delete(String timeSlotID) throws SQLException {
        try ( PreparedStatement pstm = connection.prepareCall("DELETE FROM TimeSlot WHERE TimeSlotId = ?")) {
            connection.setAutoCommit(false);
            pstm.setInt(1, Integer.parseInt(timeSlotID));
            pstm.executeUpdate();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            e.printStackTrace();
        } finally {
            connection.commit();
        }
        return false;
    }

    @Override
    public Response<Map<String, Boolean>> update(TimeSlot model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<TimeSlot>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<List<Show>> allShow() {
        List<Show> shows = new ArrayList<>();
        Show show;
        TimeSlot schedule;
        try ( PreparedStatement pstm = connection.prepareCall("select * from TimeSlot t left join Show s on t.TimeSlotID = s.TimeSlotID")) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                schedule = new TimeSlot(rs.getInt("TimeSlotID"),
                        rs.getTimestamp("StartTime"),
                        rs.getTimestamp("EndTime"),
                        roomDBContext.getRoomById(rs.getInt("RoomID"))
                );
                show = new Show(rs.getInt("ShowID"),
                        rs.getInt("StartDelay"),
                        rs.getBoolean("Status"),
                        moviesDBContext.get(new Movie(rs.getInt("MovieID"))).getReturnObject(),
                        schedule);
                shows.add(show);
            }

            return new Response<>(Response.OK, shows, "Get All successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "Fail to get");

    }

    public Response<Map<String, Boolean>> duplicateInsert(Show model) throws SQLException {
        Map<String, Boolean> result = new HashMap<>();

        try {
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO TimeSlot VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstm.setTimestamp(1, model.getTimeSlot().getStartTime());
            pstm.setTimestamp(2, model.getTimeSlot().getEndTime());
            pstm.setInt(3, model.getTimeSlot().getRoom().getRoomID());
            pstm.executeUpdate();

            ResultSet generatedKeys = pstm.getGeneratedKeys();
            int slotId;
            if (generatedKeys.next()) {
                slotId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated primary key.");
            }
            PreparedStatement pstm1 = connection.prepareCall("INSERT INTO Show VALUES (?,?,?,?)");
            pstm1.setInt(1, model.getStartDelay());
            pstm1.setBoolean(2, model.isStatus());
            pstm1.setInt(3, model.getMovie().getMid());
            pstm1.setInt(4, slotId);
            pstm1.executeUpdate();
            result.put("Insert Show Successfull", true);
            connection.commit();

            return new Response<>(Response.OK, result, "Insert Show successfully!");
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        }

        return new Response<>(Response.NOT_FOUND, null, "Fail to insert show");
    }

    public Show findShowByID(String showID) {
        Show show = new Show();
        try ( PreparedStatement pstm = connection.
                prepareCall("Select * from Show where showID = ?")) {
            pstm.setInt(1, Integer.parseInt(showID));
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                show.setShowID(rs.getInt("showid"));
                show.setStartDelay(rs.getInt("StartDelay"));
                show.setStatus(rs.getBoolean("status"));
                show.setMovie(moviesDBContext.get(new Movie(rs.getInt("MovieID"))).getReturnObject());
                show.setTimeSlot(get(new TimeSlot(rs.getInt("TimeSlotID"))).getReturnObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return show;
    }

    public Response<List<Show>> findShowByRoomID(String roomId) {
        List<Show> shows = new ArrayList<>();
        Show show;
        TimeSlot schedule;
        try ( PreparedStatement pstm = connection.
                prepareCall("select * from TimeSlot t left join Show s on t.TimeSlotID = s.TimeSlotID where roomid = ?")) {
            pstm.setInt(1, Integer.parseInt(roomId));
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                schedule = new TimeSlot(rs.getInt("TimeSlotID"),
                        rs.getTimestamp("StartTime"),
                        rs.getTimestamp("EndTime"),
                        roomDBContext.getRoomById(rs.getInt("RoomID"))
                );
                MovieDBContext movieCtx = new MovieDBContext();
                show = new Show(rs.getInt("ShowID"),
                        rs.getInt("StartDelay"),
                        rs.getBoolean("Status"),
                        movieCtx.get(new Movie(rs.getInt("MovieID"))).getReturnObject(),
                        schedule);
                shows.add(show);
            }

            return new Response<>(Response.OK, shows, "Get All successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "Fail to get");

    }
}
