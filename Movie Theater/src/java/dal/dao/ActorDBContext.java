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
import model.Cast;
import model.Direct;
import model.Director;
import model.Movie;
import model.Rate;
import model.Response;

/**
 *
 * @author HP
 */
public class ActorDBContext extends DBContext<Actor> {

    @Override
    public Response<Map<String, Boolean>> insert(Actor model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Actor> get(Actor model) {
 String sql = "SELECT m.MovieID, m.Title, m.ReleaseDate, m.Duration, r.RatingID, m.MovieDesc, \n"
                + "m.Poster, m.[URL], m.[View], m.Background,r.RatingLabel, r.RatingDesc, \n"
                + "a.ActorID, a.FirstName, a.LastName, a.DOB FROM Actor a  \n"
                + "INNER JOIN [Cast] c ON c.ActorID = a.ActorID\n"
                + "INNER JOIN Movie m ON m.MovieID = c.MovieID\n"
                + "INNER JOIN Rating r ON r.RatingID = m.RatingID\n"
                + "WHERE a.ActorID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getActorID());
            rs = stm.executeQuery();
            if (rs.next()) {
                Cast c = new Cast();
                
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
                c.setMovie(m);
                
                Rate r = new Rate();
                r.setRatingID(rs.getInt("ratingID"));
                r.setRatingDesc(rs.getString("ratingDesc"));
                r.setRatingLabel(rs.getString("ratingLabel"));
                m.setRate(r);
                
                Actor a = new Actor();
                a.setActorID(rs.getInt("actorID"));
                a.setFirstName(rs.getString("firstName"));
                a.setLastName(rs.getString("lastName"));
                a.setDob(rs.getDate("Dob"));
                c.setActor(a);
                
                
                return new Response<>(Response.OK, a, "Show retrieved successfully");
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
    public Response<Map<String, Boolean>> delete(Actor model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Actor model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Actor>> all() {
        List<Actor> actorList = new ArrayList<>();
        String sql = "select GenreID,GenreName from Genre";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Actor a = new Actor();
                a.setActorID(rs.getInt("actorID"));
                a.setFirstName(rs.getString("firstName"));
                a.setLastName(rs.getString("lastName"));
                a.setDob(rs.getDate("dob"));
                

                actorList.add(a);

            }
            return new Response<>(Response.OK, actorList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ActorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ActorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ActorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ActorDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
}
