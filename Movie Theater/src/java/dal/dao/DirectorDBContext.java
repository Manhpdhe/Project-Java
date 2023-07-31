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
import model.Actor;
import model.Direct;
import model.Director;
import model.Movie;
import model.Rate;
import model.Response;

/**
 *
 * @author HP
 */
public class DirectorDBContext extends DBContext<Director> {

    @Override
    public Response<Map<String, Boolean>> insert(Director model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Director> get(Director model) {
        String sql = "SELECT di.DirectorID, di.FirstName, di.LastName, di.DOB,\n"
                + "m.MovieID, m.Title, m.Duration, m.MovieDesc, m.ReleaseDate, m.[URL], m.Background, m.RatingID, m.Poster, m.[View],\n"
                + "r.RatingDesc, r.RatingLabel, r.RatingID\n"
                + "FROM Director di \n"
                + "INNER JOIN Direct d ON d.DirectorID = di.DirectorID \n"
                + "INNER JOIN Movie m ON m.MovieID = d.MovieID\n"
                + "INNER JOIN Rating r ON r.RatingID = m.RatingID\n"
                + "Where di.DirectorID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getDirectorID());
            rs = stm.executeQuery();
            if (rs.next()) {
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
                
                
                return new Response<>(Response.OK, di, "Show retrieved successfully");
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
    public Response<Map<String, Boolean>> delete(Director model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Director model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Director>> all() {
        List<Director> directorList = new ArrayList<>();
        String sql = "select DirectorID, FirstName, LastName, DOB from Director";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Director d = new Director();
                d.setDirectorID(rs.getInt("directorID"));
                d.setFirstName(rs.getString("firstName"));
                d.setLastName(rs.getString("lastName"));
                d.setDob(rs.getDate("dob"));

                directorList.add(d);

            }
            return new Response<>(Response.OK, directorList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(DirectorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DirectorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DirectorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DirectorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
