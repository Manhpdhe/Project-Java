/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Cinema;
import model.Response;

/**
 *
 * @author DUONG THANH LUAN
 */
public class CinemaDBContext extends DBContext<Cinema> {
    
    @Override
    public Response<Map<String,Boolean>> insert(Cinema cinema) {
        Map<String,Boolean> map = new HashMap<>();
        String sql = "Insert into Branch values (?, ?)";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setString(1, cinema.getLocation());
            pstm.setString(2, cinema.getBranchName());
            pstm.executeUpdate();
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (Exception e) {
            e.printStackTrace();
        map.put("inserted",false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Insert FAIL!");
        }
    }

    @Override
    public Response<List<Cinema>> all() {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "Select branchId, location, branchname from Branch";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            ResultSet rs = pstm.executeQuery();
            /*
            Get value of brach from db and transmit to Cinema model. SIMULTANEOUSLY, add to CINEMAS list
             */
            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setBranchID(rs.getInt("branchId"));
                cinema.setLocation(rs.getString("location"));
                cinema.setBranchName(rs.getString("branchname"));

                cinemas.add(cinema);
            }
            return new Response<>(Response.OK, cinemas, "Get all cinemas done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No cinema retrieved");
    }

    @Override
    public Response<Cinema> get(Cinema cinema) {
        ResultSet rs = null;
        String sql = "Select * from branch b where b.branchId = ?";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, cinema.getBranchID());
            rs = pstm.executeQuery();
            if (rs.next()) {
                cinema.setBranchID(rs.getInt("branchId"));
                cinema.setLocation(rs.getString("location"));
                cinema.setBranchName(rs.getString("branchname"));
            }
            return new Response<>(Response.OK, cinema, "Get cinema by id done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No cinema found retrieved");
    }

    @Override
    public Response<Map<String,Boolean>> update(Cinema cinema) {
        Map<String,Boolean> map = new HashMap<>();
//        List<Seat> seats = new ArrayList<>();
//        ResultSet rs = null;
        String sql = "update branch set location = ?, branchName = ? where branchId = ?";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setString(1, cinema.getLocation());
            pstm.setString(2, cinema.getBranchName());
            pstm.setInt(3, cinema.getBranchID());
            pstm.executeUpdate();
            map.put("updated", true);
            return new Response<>(Response.OK, map, "Update SUCCESS!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("updated", false);
            return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Update FAIL!");
    }
    
    @Override
    public Response<Map<String, Boolean>> delete(Cinema cinema) {
        Map<String,Boolean> map = new HashMap<>();
        Map<String,Boolean> status = new HashMap<>();
        String sql = "delete from Branch where branchID = ?";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, cinema.getBranchID());
            pstm.executeUpdate();
            return new Response<>(Response.OK, status, "Delete SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        } 
        status.put("delete", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Delete fail!");
    }
    
    public Response<Cinema> getByBranchID(String branchID) {
        ResultSet rs = null;
        String sql = "Select * from branch b where b.branchId = ?";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, Integer.parseInt(branchID));
            rs = pstm.executeQuery();
            Cinema cinema = new Cinema();
            if (rs.next()) {
                cinema.setBranchID(rs.getInt("branchId"));
                cinema.setLocation(rs.getString("location"));
                cinema.setBranchName(rs.getString("branchname"));
            }
            return new Response<>(Response.OK, cinema, "Get cinema by id done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No cinema found retrieved");
    }

}
