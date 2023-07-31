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
public class ClassifyDBContext extends DBContext<Classify> {

    public Response<List<Classify>> getGenreByMovieID(Movie movie) {
    List<Classify> classifyList = new ArrayList<>();
    String sql = "SELECT g.GenreID, g.GenreName, m.MovieID, m.Title, m.ReleaseDate, m.Duration, r.RatingID, m.MovieDesc, \n"
            + "m.Poster, m.[URL], m.[View], m.Background, r.RatingDesc, r.RatingLabel FROM Classify c \n"
            + "INNER JOIN Genre g ON g.GenreID = c.GenreID\n"
            + "INNER JOIN Movie m ON m.MovieID = c.MovieID\n"
            + "INNER JOIN Rating r ON r.RatingID = m.RatingID\n"
            + "WHERE m.MovieID = ?";
    ResultSet rs = null;
    PreparedStatement stm = null;
    try {
        stm = connection.prepareStatement(sql);
        stm.setInt(1, movie.getMid());
        rs = stm.executeQuery();
        while (rs.next()) {
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
            c.setMovie(movie);

            Rate r = new Rate();
            r.setRatingID(rs.getInt("ratingID"));
            r.setRatingDesc(rs.getString("ratingDesc"));
            r.setRatingLabel(rs.getString("ratingLabel"));
            m.setRate(r);

            classifyList.add(c);
        }

        if (!classifyList.isEmpty()) {
            return new Response<>(Response.OK, classifyList, "Genres retrieved successfully");
        }
    } catch (SQLException ex) {
        Logger.getLogger(ClassifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }

    return new Response<>(Response.NOT_FOUND, null, "No genres retrieved");
}

    @Override
    public Response<Map<String, Boolean>> insert(Classify model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Classify> get(Classify model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Classify model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Classify model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Classify>> all() {
        List<Classify> classifyList = new ArrayList<>();
        String sql = "select c.MovieID, g.GenreID, g.GenreName \n"
                + "from Classify c join Genre g \n"
                + "on g.GenreID = c.GenreID  ";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {

            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Classify c = new Classify();

                Genre g = new Genre();
                g.setGenreID(rs.getInt("genreID"));
                g.setGenreName(rs.getString("genreName"));
                c.setGenre(g);

                Movie m = new Movie();
                m.setMid(rs.getInt("movieID"));
                c.setMovie(m);

                classifyList.add(c);

            }
            return new Response<>(Response.OK, classifyList, "All movie are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ClassifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ClassifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ClassifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(ClassifyDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
}
