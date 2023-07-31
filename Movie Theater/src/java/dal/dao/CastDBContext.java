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
import model.Classify;
import model.Genre;
import model.Movie;
import model.Rate;
import model.Response;

/**
 *
 * @author HP
 */
public class CastDBContext extends DBContext<Cast> {

    public Response<List<Cast>> getByMovieID(Movie movie) {
        List<Cast> castList = new ArrayList<>();
        String sql = "SELECT m.MovieID, m.Title, m.ReleaseDate, m.Duration, r.RatingID, m.MovieDesc, \n"
                + "m.Poster, m.[URL], m.[View], m.Background,r.RatingLabel, r.RatingDesc, \n"
                + "a.ActorID, a.FirstName, a.LastName, a.DOB FROM [Cast] c \n"
                + "INNER JOIN Actor a ON a.ActorID = c.ActorID\n"
                + "INNER JOIN Movie m ON m.MovieID = c.MovieID\n"
                + "INNER JOIN Rating r ON r.RatingID = m.RatingID\n"
                + "WHERE m.MovieID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, movie.getMid());

            rs = stm.executeQuery();
            while (rs.next()) {
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

                castList.add(c);
            }
            return new Response<>(Response.OK, castList, "Directors are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No directors retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> insert(Cast model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Cast> get(Cast model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Cast model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Cast model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Cast>> all() {
        List<Cast> castList = new ArrayList<>();
        String sql = "select c.MovieID, g.GenreID, g.GenreName "
                + "from Classify c, Genre g "
                + "where g.GenreID = c.GenreID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Cast c = new Cast();

                Actor a = new Actor();
                a.setActorID(rs.getInt("actorID"));
                a.setFirstName(rs.getString("firstName"));
                a.setLastName(rs.getString("lastName"));
                a.setDob(rs.getDate("dob"));
                c.setActor(a);

                Movie m = new Movie();
                m.setMid(rs.getInt("movieID"));
                c.setMovie(m);

                castList.add(c);

            }
            return new Response<>(Response.OK, castList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(CastDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(CastDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(CastDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(CastDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
