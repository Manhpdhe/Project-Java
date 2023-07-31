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
import model.BranchMovie;
import model.Movie;
import model.Rate;
import model.Response;

/**
 *
 * @author CaoThuLuDau
 */
public class BranchMovieDBContext extends DBContext<BranchMovie> {
    @Override
    public Response<Map<String,Boolean>> insert(BranchMovie branchMovie) {
        Map<String,Boolean> map = new HashMap<>();
        String sql = "INSERT INTO [BranchMovie]\n" +
                     "(BranchID, MovieID)\n" +
                     "VALUES\n" +
                     "(?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            
            stm.setInt(1, branchMovie.getBranch().getBranchID());
            stm.setInt(2, branchMovie.getMovie().getMid());
            
            stm.executeUpdate();
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        map.put("inserted",false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Insert FAIL!");
    }
    
    @Override
    public Response<List<BranchMovie>> all() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

//        List<BranchMovie> branchMovieList = new ArrayList<>();
//        String sql = "SELECT BranchMovieID, Location, BranchMovieName FROM BranchMovie";
//        PreparedStatement stm = null;
//        ResultSet rs = null;
//        try {
//            stm = connection.prepareStatement(sql);
//            rs = stm.executeQuery();
//            while(rs.next())
//            { 
//                BranchMovie r = new BranchMovie();
//                r.setBranchMovieID(rs.getInt("BranchMovieID"));
//                r.setLocation(rs.getNString("Location"));
//                r.setBranchMovieName(rs.getNString("BranchMovieName"));
//                branchMovieList.add(r);   
//            }
//            return new Response<>(Response.OK, branchMovieList, "All branchMovies are retrieved successfully");
//        } catch (SQLException ex) {
//            Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            //handle exceptions individually and ensure that
//            //each resource is closed even if an exception occurs while closing a previous resource.
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    @Override
    public Response<Map<String,Boolean>> update(BranchMovie branchMovie) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

//        Map<String,Boolean> map = new HashMap<>();
//        String sql = "UPDATE [BranchMovie]\n" +
//                     "SET Location = ?,\n" +
//                     "    BranchMovieName = ?,\n" +
//                     "WHERE BranchMovieID = ?";
//        PreparedStatement stm = null;
//        try {
//            stm = connection.prepareStatement(sql);
//            
//            stm.setString(1, branchMovie.getLocation());     
//            stm.setString(2, branchMovie.getBranchMovieName());
//            stm.setInt(3, branchMovie.getBranchMovieID());
//            
//            stm.executeUpdate();
//            map.put("updated", true);
//            return new Response<>(Response.OK, map, "Update SUCCESS!");
//        } catch (SQLException ex) {
//            Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//         map.put("updated", false);
//         return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Update FAIL!");
    }
    
    @Override
    public Response<Map<String, Boolean>> delete(BranchMovie branchMovie) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

//        Map<String,Boolean> status = new HashMap<>();
//        String sql = "DELETE [BranchMovie] WHERE BranchMovieID = ?";
//        PreparedStatement stm = null;
//        try {
//            stm = connection.prepareStatement(sql);
//            
//            stm.setInt(1, branchMovie.getBranchMovieID());
//            
//            stm.executeUpdate();
//            
//            status.put("deleted", true);
//            return new Response<>(Response.OK, status, "Delete SUCCESS");
//        } catch (SQLException ex) {
//            Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                stm.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                connection.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(BranchMovieDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        status.put("delete", false);
//        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Delete fail!");
    }

    @Override
    public Response<BranchMovie> get(BranchMovie model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Response<List<Movie>> getMoviesByBranchID(int branchID) {
        List<Movie> movieList = new ArrayList<>();
        String sql = "select cm.BranchID, cm.MovieID, m.Title, m.ReleaseDate, m.Duration, m.RatingID,\n" +
                    " m.MovieDesc, m.Poster, m.[View], m.Background, r.RatingID, r.RatingDesc, r.RatingLabel\n" +
                    " from CinemaMovie cm \n" +
                    " inner join Movie m on cm.MovieID = m.MovieID\n" +
                    " inner join Rating r on  m.RatingID = r.RatingID\n" +
                    " where BranchID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, branchID);
            
            rs = stm.executeQuery();
            while(rs.next())
            { 
                Movie m = new Movie();
                m.setMid(rs.getInt("MovieID"));
                m.setTitle(rs.getString("Title"));
                m.setRedob(rs.getDate("ReleaseDate"));
                m.setDuration(rs.getInt("Duration"));
                m.setMdesc(rs.getString("MovieDesc"));
                m.setPoster(rs.getString("Poster"));
                m.setView(rs.getInt("View"));
                m.setBg(rs.getString("Background"));

                Rate r = new Rate();
                r.setRatingID(rs.getInt("RatingID"));
                r.setRatingDesc(rs.getNString("RatingDesc"));
                r.setRatingLabel(rs.getString("RatingLabel"));
                m.setRate(r);

                movieList.add(m);
            }
            return new Response<>(Response.OK, movieList, "Branch movies are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
}
