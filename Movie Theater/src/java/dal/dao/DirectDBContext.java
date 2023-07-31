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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Direct;
import model.Director;
import model.Movie;
import model.Rate;
import model.Response;

/**
 *
 * @author HP
 */
public class DirectDBContext extends DBContext<Direct> {

     public Response<List<Direct>> getByMovieID(Movie movie) {
        List<Direct> directList = new ArrayList<>();
        String sql = "SELECT m.MovieID, m.Title, m.ReleaseDate, m.Duration, r.RatingID, m.MovieDesc, \n"
                + "m.Poster, m.[URL], m.[View], m.Background,r.RatingLabel, r.RatingDesc, \n"
                + "di.DirectorID, di.FirstName, di.LastName, di.DOB FROM Direct d \n"
                + "INNER JOIN Director di ON di.DirectorID = d.DirectorID\n"
                + "INNER JOIN Movie m ON m.MovieID = d.MovieID\n"
                + "INNER JOIN Rating r ON r.RatingID = m.RatingID\n"
                + "WHERE m.MovieID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, movie.getMid());

            rs = stm.executeQuery();
            while (rs.next()) {
                Direct d = new Direct();

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
                d.setMovie(m);

                Rate r = new Rate();
                r.setRatingID(rs.getInt("ratingID"));
                r.setRatingDesc(rs.getString("ratingDesc"));
                r.setRatingLabel(rs.getString("ratingLabel"));
                m.setRate(r);

                Director di = new Director();
                di.setDirectorID(rs.getInt("directorID"));
                di.setFirstName(rs.getString("firstName"));
                di.setLastName(rs.getString("lastName"));
                di.setDob(rs.getDate("Dob"));
                d.setDirector(di);

                directList.add(d);
            }
            return new Response<>(Response.OK, directList, "Directors are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No directors retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> insert(Direct model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Direct> get(Direct model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Direct model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Direct model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Direct>> all() {
        List<Direct> directList = new ArrayList<>();
        String sql = "select d.MovieID, d1.DirectorID, d1.FirstName, d1.LastName, d1.DOB "
                + "from Direct d, Director d1 "
                + "where d.DirectorID=d1.DirectorID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Direct d = new Direct();

                Director d1 = new Director();
                d1.setDirectorID(rs.getInt("directorID"));
                d1.setFirstName(rs.getString("firstName"));
                d1.setLastName(rs.getString("lastName"));
                d1.setDob(rs.getDate("dob"));
                d.setDirector(d1);

                Movie m = new Movie();
                m.setMid(rs.getInt("movieID"));
                d.setMovie(m);

                directList.add(d);

            }
            return new Response<>(Response.OK, directList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(DirectDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DirectDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DirectDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DirectDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
