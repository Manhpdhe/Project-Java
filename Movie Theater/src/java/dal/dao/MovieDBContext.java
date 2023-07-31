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
import model.Actor;
import model.Branch;
import model.Cast;
import model.Classify;
import model.Direct;
import model.Director;
import model.Genre;
import model.Language;
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
public class MovieDBContext extends DBContext<Movie> {

    public List<Movie> getMovieByBranchId(int branchId) {
        List<Movie> list = new ArrayList<>();
        String query = "SELECT m.MovieID, m.Title, m.ReleaseDate, m.Duration, m.RatingID, m.Poster,\n"
                + "m.[View], m.Background, r.RatingLabel, r.RatingDesc, s.ShowID, s.StartDelay, \n"
                + "s.[Status], sc.TimeSlotID, sc.StartTime, sc.EndTime, ro.RoomID, ro.NumCols, ro.NumRows,\n"
                + "ro.RoomName, b.BranchID, b.BranchName, b.[Location] \n"
                + "FROM Movie m\n"
                + "INNER JOIN Rating r ON m.RatingID = r.RatingID \n"
                + "INNER JOIN Show s ON m.MovieID = s.MovieID \n"
                + "INNER JOIN TimeSlot sc ON s.TimeSlotID = sc.TimeSlotID\n"
                + "INNER JOIN Room ro ON ro.RoomID = sc.RoomID \n"
                + "INNER JOIN Branch b ON b.BranchID = ro.BranchID \n"
                + "WHERE b.BranchID = ?";

        try ( PreparedStatement stm = connection.prepareStatement(query)) {
            stm.setInt(1, branchId);

            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setMid(rs.getInt("MovieID"));
                    movie.setTitle(rs.getString("Title"));
                    movie.setRedob(rs.getDate("ReleaseDate"));
                    movie.setDuration(rs.getInt("Duration"));
                    movie.setPoster(rs.getString("Poster"));
                    movie.setView(rs.getInt("View"));
                    movie.setBg(rs.getString("Background"));

                    Rate rate = new Rate();
                    rate.setRatingID(rs.getInt("RatingID"));
                    rate.setRatingLabel(rs.getString("RatingLabel"));
                    rate.setRatingDesc(rs.getString("RatingDesc"));
                    movie.setRate(rate);

                    Show s = new Show();
                    s.setStartDelay(rs.getInt("StartDelay"));           
                    s.setStatus(true);
                    s.setShowID(rs.getInt("ShowID"));

                    TimeSlot sc = new TimeSlot();
                    sc.setStartTime(rs.getTimestamp("StartTime"));
                    sc.setEndTime(rs.getTimestamp("EndTime"));
                    sc.setTimeSlotId(rs.getInt("TimeSlotID"));
                    s.setTimeSlot(sc);

                    Room ro = new Room();
                    ro.setNumCols(rs.getInt("NumCols"));
                    ro.setNumRows(rs.getInt("NumRows"));
                    ro.setRoomID(rs.getInt("RoomID"));
                    ro.setRoomName(rs.getString("RoomName"));
                    sc.setRoom(ro);

                    Branch b = new Branch();
                    b.setBranchID(rs.getInt("BranchID"));
                    b.setBranchName(rs.getString("BranchName"));
                    b.setLocation(rs.getString("Location"));
                    ro.setBranch(b);

                    list.add(movie);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

   public List<Movie> searchByMovie(String txtSearch) {
        List<Movie> list = new ArrayList<>();
        String query = "SELECT m.MovieID, m.Title, m.ReleaseDate, m.Duration, m.RatingID, m.Poster,\n"
                + "m.[View], m.Background, r.RatingLabel, r.RatingDesc\n"
                + "FROM Movie m\n"
                + "INNER JOIN Rating r ON m.RatingID = r.RatingID \n"
                + "WHERE m.Title LIKE ?";

        try ( PreparedStatement stm = connection.prepareStatement(query)) {
            String searchPattern = "%" + txtSearch + "%";
            stm.setString(1, searchPattern);

            try ( ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setMid(rs.getInt("MovieID"));
                    movie.setTitle(rs.getString("Title"));
                    movie.setRedob(rs.getDate("ReleaseDate"));
                    movie.setDuration(rs.getInt("Duration"));
                    movie.setPoster(rs.getString("Poster"));
                    movie.setView(rs.getInt("View"));
                    movie.setBg(rs.getString("Background"));

                    Rate rate = new Rate();
                    rate.setRatingID(rs.getInt("RatingID"));
                    rate.setRatingLabel(rs.getString("RatingLabel"));
                    rate.setRatingDesc(rs.getString("RatingDesc"));
                    movie.setRate(rate);

                    list.add(movie);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    @Override
    public Response<Map<String, Boolean>> insert(Movie movie) {
        String sql = "INSERT INTO Movie ([Title],\n"
                + "ReleaseDate, \n"
                + "Duration, \n"
                + "RatingID, \n"
                + "MovieDesc,"
                + "Poster,"
                + "LanguageID, \n"
                + "[View], \n"
                + "Background,\n"
                + "[URL])\n"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, movie.getTitle());
            stm.setDate(2, movie.getRedob());
            stm.setInt(3, movie.getDuration());
            stm.setInt(4, movie.getRate().getRatingID());
            stm.setString(5, movie.getMdesc());
            stm.setString(6, movie.getPoster());
            stm.setInt(7, movie.getLanguage().getLanguageId());
            stm.setInt(8, movie.getView());
            stm.setString(9, movie.getBg());
            stm.setString(10, movie.getURL());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        finally {
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
//        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");

    }

    public Response<Movie> findMoviesByID(Integer movieId) {
        Movie movie = new Movie();
        ResultSet rs = null;
        String sql = "select * from Movie m where m.MovieID = ?";
        try ( PreparedStatement stm = connection.prepareCall(sql)) {
            stm.setInt(1, movieId);
            rs = stm.executeQuery();
            if (rs.next()) {
                movie.setMid(rs.getInt("MovieID"));
                movie.setTitle(rs.getString("Title"));
                movie.setRedob(rs.getDate("ReleaseDate"));
                movie.setDuration(rs.getInt("Duration"));
                movie.setMdesc(rs.getString("MovieDesc"));
                movie.setPoster(rs.getString("Poster"));
                movie.setView(rs.getInt("View"));
                movie.setBg(rs.getString("Background"));
            }
            return new Response<>(Response.OK, movie, "Get movie done");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");

    }

    @Override
    public Response<Movie> get(Movie movie) {
        String sql = "select m.MovieID, m.Title, m.ReleaseDate, m.Duration, m.LanguageID, r.RatingID, m.MovieDesc, \n" +
"                m.Poster, m.[URL], m.[View], m.Background,r.RatingLabel, r.RatingDesc,\n" +
"				l.LanguageCode, l.LanguageName\n" +
"                from Movie m  INNER JOIN Rating r ON m.RatingID = r.RatingID \n" +
"				INNER JOIN [Language] l ON m.LanguageID = l.LanguageID\n" +
"                where MovieID = ?";
        ResultSet rs = null;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, movie.getMid());
            rs = stm.executeQuery();
            if (rs.next()) {
                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));
                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));
                m.setURL(rs.getString("URL"));

                Rate r = new Rate();
                r.setRatingID(rs.getInt("RatingID"));
                r.setRatingLabel(rs.getString("RatingLabel"));
                r.setRatingDesc(rs.getString("RatingDesc"));
                m.setRate(r);
                
                Language l = new Language();
                l.setLanguageId(rs.getInt("languageId"));
                l.setLanguageName(rs.getString("languageName"));
                l.setLanguageCode(rs.getString("languageCode"));
                m.setLanguage(l);
                
                
                return new Response<>(Response.OK, m, "Show retrieved successfully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
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

    public Response<Movie> getByTitle(String title) {
        String sql = "select m.MovieID, m.Title, m.ReleaseDate, m.Duration, r.RatingID, m.MovieDesc, \n"
                + "m.Poster, m.[URL], m.[View], m.Background,r.RatingLabel, r.RatingDesc \n"
                + "from Movie m  INNER JOIN Rating r ON m.RatingID = r.RatingID \n"
                + "where Title = ?";
        ResultSet rs = null;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, title);
            rs = stm.executeQuery();
            if (rs.next()) {
                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));
                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));
                m.setURL(rs.getString("URL"));

                Rate r = new Rate();
                r.setRatingID(rs.getInt("RatingID"));
                r.setRatingLabel(rs.getString("RatingLabel"));
                r.setRatingDesc(rs.getString("RatingDesc"));
                m.setRate(r);

                return new Response<>(Response.OK, m, "Show retrieved successfully");

            }
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Movie movie) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE [Movie] WHERE MovieID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, movie.getMid());

            stm.executeUpdate();
            status.put("process", true);

            return new Response<>(Response.OK, status, "Delete successfully");
        } catch (SQLException ex) {
            status.put("process", false);
            ex.printStackTrace();
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Cannot delete movie!");
    }

    @Override
    public Response<Map<String, Boolean>> update(Movie movie) {
        String sql = "UPDATE Movie "
                + "SET Title = ?, \n"
                + "ReleaseDate = ?, \n"
                + "Duration = ?, \n"
                + "RatingID = ?, \n"
                + "MovieDesc = ?,\n"
                + "Poster = ?, \n"
                + "LanguageID =?,\n"
                + "[View] = ?,\n"
                + "Background = ?,\n"
                + "URL =?\n"
                + "WHERE MovieID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, movie.getTitle());
            stm.setDate(2, movie.getRedob());
            stm.setInt(3, movie.getDuration());
            stm.setInt(4, movie.getRate().getRatingID());
            stm.setString(5, movie.getMdesc());
            stm.setString(6, movie.getPoster());
            stm.setInt(7, movie.getLanguage().getLanguageId());
            stm.setInt(8, movie.getView());
            stm.setString(9, movie.getBg());
            stm.setString(9, movie.getURL());
            stm.setInt(10, movie.getMid());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");

    }

    @Override
    public Response<List<Movie>> all() {
        List<Movie> movieList = new ArrayList<>();
        String sql = "Select m.MovieID,\n"
                + "m.Title, m.ReleaseDate, m.Duration, m.RatingID,\n"
                + "r.RatingDesc, r.RatingLabel,\n"
                + "m.MovieDesc, m.Poster,\n"
                + "m.[View] ,m.Background, m.[URL]\n"
                + "from Movie m INNER JOIN Rating r ON m.RatingID = r.RatingID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {

                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));
                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));
                m.setURL(rs.getString("URL"));

                Rate r = new Rate();
                r.setRatingID(rs.getInt("RatingID"));
                r.setRatingDesc(rs.getNString("RatingDesc"));
                r.setRatingLabel(rs.getString("RatingLabel"));
                m.setRate(r);

                movieList.add(m);
            }
            return new Response<>(Response.OK, movieList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(MovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
}
