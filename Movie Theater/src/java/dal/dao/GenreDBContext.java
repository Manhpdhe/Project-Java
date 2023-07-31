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
import model.Classify;
import model.Genre;
import model.Movie;
import model.Rate;
import model.Response;

/**
 *
 * @author HP
 */
public class GenreDBContext extends DBContext<Genre> {

    @Override
    public Response<Map<String, Boolean>> insert(Genre model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Genre> get(Genre model) {
        String sql = "select g.GenreID, g.GenreName, m.MovieID, m.Title, m.ReleaseDate, m.Duration, r.RatingID, m.MovieDesc, \n"
                + "m.Poster, m.[URL], m.[View], m.Background, r.RatingDesc, r.RatingLabel from Genre g  \n"
                + "INNER JOIN Classify c  ON c.GenreID = g.GenreID\n"
                + "INNER JOIN Movie m ON m.MovieID = c.MovieID\n"
                + "INNER JOIN Rating r ON r.RatingID = m.RatingID\n"
                + "WHERE g.GenreID = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getGenreID());
            rs = stm.executeQuery();
            if (rs.next()) {
                Classify c = new Classify();

                Genre g = new Genre();
                g.setGenreID(rs.getInt("genreID"));
                g.setGenreName(rs.getString("genreName"));
                c.setGenre(g);

                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));
                c.setMovie(m);

                Rate r = new Rate();
                r.setRatingID(rs.getInt("ratingID"));
                r.setRatingDesc(rs.getString("ratingDesc"));
                r.setRatingLabel(rs.getString("ratingLabel"));
                m.setRate(r);

                return new Response<>(Response.OK, g, "Show retrieved successfully");
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
    public Response<Map<String, Boolean>> delete(Genre model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Genre model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Genre>> all() {
        List<Genre> genreList = new ArrayList<>();
        String sql = "select GenreID,GenreName from Genre";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Genre g = new Genre();
                g.setGenreID(rs.getInt("genreID"));
                g.setGenreName(rs.getString("genreName"));

                genreList.add(g);

            }
            return new Response<>(Response.OK, genreList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(GenreDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(GenreDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(GenreDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(GenreDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
}
