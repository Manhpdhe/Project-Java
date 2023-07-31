/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Branch;
import model.Movie;
import model.Rate;
import model.Response;
import model.Room;
import model.Schedule;
import model.Show;
import model.TimeSlot;

/**
 *
 * @author HP
 */
public class ShowDBContext extends DBContext<Show> {

    public Response<List<Show>> getShowbyMovie(Movie movie) {
        List<Show> showList = new ArrayList<>();
        String sql = "SELECT s.ShowID, ts.StartTime, ts.EndTime, s.[Status],\n"
                + "m.MovieID, m.Title, m.ReleaseDate, m.Duration, \n"
                + "r.RatingID, r.RatingLabel, r.RatingDesc, m.MovieDesc, m.Poster, m.[View], m.Background,\n"
                + "ts.TimeSlotID, ro.RoomID, ro.RoomName, ro.NumRows, ro.NumCols,\n"
                + "b.BranchID, b.BranchName, b.[Location] FROM\n"
                + "Show s INNER JOIN Movie m ON s.MovieID = m.MovieID\n"
                + "INNER JOIN Rating r ON m.RatingID = r.RatingID\n"
                + "INNER JOIN TimeSlot ts ON s.TimeSlotID = ts.TimeSlotID\n"
                + "INNER JOIN Room ro ON ts.RoomID = ro.RoomID\n"
                + "INNER JOIN Branch b ON ro.BranchID = b.BranchID\n"
                + "WHERE m.MovieID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, movie.getMid());
            rs = stm.executeQuery();
            while (rs.next()) {
                Show s = new Show();
                s.setShowID(rs.getInt("ShowID"));
                s.setStatus(rs.getBoolean("Status"));

                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));
                s.setMovie(m);

                Rate rat = new Rate();
                rat.setRatingID(rs.getInt("RatingID"));
                rat.setRatingLabel(rs.getString("RatingLabel"));
                rat.setRatingDesc(rs.getString("RatingDesc"));

                m.setRate(rat);

                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));

                s.setMovie(m);

//              Schedule sch = new Schedule();
//              sch.setScheduleId(rs.getInt("ScheduleID"));
//              sch.setOpenTime(rs.getTimestamp("OpenTime"));
//              sch.setCloseTime(rs.getTimestamp("CloseTime"));
                TimeSlot ts = new TimeSlot();
                ts.setTimeSlotId(rs.getInt("TimeSlotID"));
                ts.setStartTime(rs.getTimestamp("StartTime"));
                ts.setEndTime(rs.getTimestamp("EndTime"));

                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));

                Branch b = new Branch();
                b.setBranchID(rs.getInt("BranchID"));
                b.setBranchName(rs.getString("BranchName"));
                b.setLocation(rs.getString("Location"));

                r.setBranch(b);

                ts.setRoom(r);

                s.setTimeSlot(ts);

                showList.add(s);
            }
            return new Response<>(Response.OK, showList, "Show retrieved successfully");

        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No show retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> insert(Show show) {
        String sql = "INSERT INTO Show (\n"
                + "[StartDelay],\n"
                + "Status,\n"
                + "MovieID, \n"
                + "TimeSlotID\n"
                + ") VALUES (?,?,?,?)";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, show.getStartDelay());
            stm.setBoolean(2, show.isStatus());
            stm.setInt(3, show.getMovie().getMid());
            stm.setInt(4, show.getTimeSlot().getTimeSlotId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");

    }

    public Response<Map<String, Boolean>> deleteShowsByMovie(Movie movie) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE [Show] WHERE MovieID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, movie.getMid());

            stm.executeUpdate();
            status.put("processSuccessful", true);

            return new Response<>(Response.OK, status, "Delete successfully");
        } catch (SQLException ex) {

            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        status.put("processSuccessful", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Cannot delete movie!");
    }

    public Response<Integer> countShowsByMovie(Movie movie) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "SELECT COUNT(ShowID) AS NumShows FROM Show WHERE MovieID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, movie.getMid());

            rs = stm.executeQuery();
            if (rs.next()) {
                int numShows = rs.getInt("NumShows");
                return new Response<>(Response.OK, numShows, "Retrieve number of shows successfully");
            }

        } catch (SQLException ex) {

            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.INTERNAL_SERVER_ERROR, -1, "Retrieve number of shows fail!");
    }

    @Override
    public Response<Show> get(Show model) {
        String sql = "SELECT s.ShowID, ts.StartTime, ts.EndTime, s.StartDelay, s.[Status],\n"
                + "m.MovieID, m.Title, m.ReleaseDate, m.Duration, \n"
                + "r.RatingID, r.RatingLabel, r.RatingDesc, m.MovieDesc, m.Poster, m.[View], m.Background,\n"
                + "ts.TimeSlotID, ro.RoomID, ro.RoomName, ro.NumRows, ro.NumCols,\n"
                + "b.BranchID, b.BranchName, b.[Location] FROM\n"
                + "Show s INNER JOIN Movie m ON s.MovieID = m.MovieID\n"
                + "INNER JOIN Rating r ON m.RatingID = r.RatingID\n"
                + "INNER JOIN TimeSlot ts ON s.TimeSlotID = ts.TimeSlotID\n"
                + "INNER JOIN Room ro ON ts.RoomID = ro.RoomID\n"
                + "INNER JOIN Branch b ON ro.BranchID = b.BranchID\n"
                + "WHERE s.ShowID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getShowID());
            rs = stm.executeQuery();
            if (rs.next()) {
                Show s = new Show();
                s.setShowID(rs.getInt("ShowID"));
                s.setStartDelay(rs.getInt("StartDelay"));
                s.setStatus(rs.getBoolean("Status"));

                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));

                Rate rat = new Rate();
                rat.setRatingID(rs.getInt("RatingID"));
                rat.setRatingLabel(rs.getString("RatingLabel"));
                rat.setRatingDesc(rs.getString("RatingDesc"));

                m.setRate(rat);

                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));

                s.setMovie(m);

                TimeSlot ts = new TimeSlot();
                ts.setTimeSlotId(rs.getInt("TimeSlotID"));
                ts.setStartTime(rs.getTimestamp("StartTime"));
                ts.setEndTime(rs.getTimestamp("EndTime"));

                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));

                Branch b = new Branch();
                b.setBranchID(rs.getInt("BranchID"));
                b.setBranchName(rs.getString("BranchName"));
                b.setLocation(rs.getString("Location"));

                r.setBranch(b);

                ts.setRoom(r);

                s.setTimeSlot(ts);

                return new Response<>(Response.OK, s, "Show retrieved successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No show retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Show show) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE [Show] WHERE ShowID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, show.getShowID());

            stm.executeUpdate();
            status.put("processSuccessful", true);

            return new Response<>(Response.OK, status, "Delete successfully");
        } catch (SQLException ex) {

            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        status.put("processSuccessful", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Cannot delete movie!");
    }

    @Override
    public Response<Map<String, Boolean>> update(Show show) {
        String sql = "UPDATE Show \n"
                + "SET StartDelay,\n"
                + "Status = ?,\n"
                + "MovieID = ?,\n"
                + "TimeSlotID =?\n"
                + "WHERE ShowID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, show.getStartDelay());
            stm.setBoolean(2, show.isStatus());
            stm.setInt(3, show.getMovie().getMid());
            stm.setInt(4, show.getTimeSlot().getTimeSlotId());
            stm.setInt(5, show.getShowID());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    @Override
    public Response<List<Show>> all() {
        List<Show> showList = new ArrayList<>();
        String sql = "SELECT s.ShowID, s.StartDelay, s.[Status],\n"
                + "m.MovieID, m.Title, m.ReleaseDate, m.Duration, "
                + "r.RatingID, r.RatingLabel, r.RatingDesc, "
                + "m.MovieDesc, m.Poster, m.[View], m.Background,\n"
                + "sc.TimeSlotID, sc.StartTime, sc.EndTime, "
                + "ro.RoomID, ro.RoomName, ro.NumRows, ro.NumCols,\n"
                + "b.BranchID, b.BranchName, b.[Location] FROM\n"
                + "Show s INNER JOIN Movie m ON s.MovieID = m.MovieID\n"
                + "INNER JOIN Rating r ON m.RatingID = r.RatingID\n"
                + "INNER JOIN TimeSlot sc ON s.TimeSlotID = sc.TimeSlotID\n"
                + "INNER JOIN Room ro ON sc.RoomID = ro.RoomID\n"
                + "INNER JOIN Branch b ON ro.BranchID = b.BranchID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Show s = new Show();
                s.setShowID(rs.getInt("ShowID"));
                s.setStartDelay(rs.getInt("startDelay"));
                s.setStatus(rs.getBoolean("Status"));

                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));

                Rate rat = new Rate();
                rat.setRatingID(rs.getInt("RatingID"));
                rat.setRatingLabel(rs.getString("RatingLabel"));
                rat.setRatingDesc(rs.getString("RatingDesc"));

                m.setRate(rat);

                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));

                s.setMovie(m);

//                Schedule sch = new Schedule();
//                sch.setScheduleId(rs.getInt("ScheduleID"));
//                sch.setOpenTime(rs.getTimestamp("OpenTime"));
//                sch.setCloseTime(rs.getTimestamp("CloseTime"));
                TimeSlot ts = new TimeSlot();
                ts.setTimeSlotId(rs.getInt("TimeSlotID"));
                ts.setStartTime(rs.getTimestamp("StartTime"));
                ts.setEndTime(rs.getTimestamp("EndTime"));

                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));

                Branch b = new Branch();
                b.setBranchID(rs.getInt("BranchID"));
                b.setBranchName(rs.getString("BranchName"));
                b.setLocation(rs.getString("Location"));

                r.setBranch(b);

                ts.setRoom(r);

                s.setTimeSlot(ts);

//                sch.setRoom(r);
//                s.setSchedule(sch);
                showList.add(s);

            }
            return new Response<>(Response.OK, showList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    public Response<Integer> countShowsByMovieInBranch(int movieID, int branchID) {
        String sql = "SELECT COUNT(ShowID) AS NumShows\n"
                + "FROM Show sh\n"
                + "INNER JOIN TimeSlot sc ON sh.TimeSlotID = sc.TimeSlotID\n"
                + "INNER JOIN Room r ON r.RoomID = sc.RoomID\n"
                + "WHERE r.BranchID = ? and sh.MovieID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, movieID);
            stm.setInt(2, branchID);

            rs = stm.executeQuery();
            if (rs.next()) {
                int numShows = rs.getInt("NumShows");
                return new Response<>(Response.OK, numShows, "Retrieve number of shows successfully");
            }

        } catch (SQLException ex) {

            Logger.getLogger(ShowDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.INTERNAL_SERVER_ERROR, -1, "Retrieve number of shows fail!");
    }

    public List<Show> findShowByMovieIdAndBranchId(int mid, int branchId) {
        List<Show> shows = new ArrayList<>();
        String sql = "select * from Show s inner join TimeSlot t on s.TimeSlotID = t.TimeSlotID join Room r on r.RoomID = t.RoomID\n"
                + "where t.StartTime >= CURRENT_TIMESTAMP and s.MovieID = ? and r.BranchID = ? order by StartTime asc";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, mid);
            pstm.setInt(2, branchId);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Show s = new Show();
                s.setShowID(rs.getInt("ShowID"));
                s.setStartDelay(rs.getInt("startDelay"));
                s.setStatus(rs.getBoolean("Status"));

                TimeSlot ts = new TimeSlot();
                ts.setTimeSlotId(rs.getInt("TimeSlotID"));
                ts.setStartTime(rs.getTimestamp("StartTime"));
                ts.setEndTime(rs.getTimestamp("EndTime"));
                s.setTimeSlot(ts);
                // Thêm Show được tìm vào trong chuỗi
                shows.add(s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shows;
    }
}
