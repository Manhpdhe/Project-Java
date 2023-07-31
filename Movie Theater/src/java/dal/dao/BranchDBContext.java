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
import model.Response;
import model.Branch;

/**
 *
 * @author CaoThuLuDau
 */
public class BranchDBContext extends DBContext<Branch> {

    @Override
    public Response<Map<String, Boolean>> insert(Branch branch) {
        Map<String, Boolean> map = new HashMap<>();
        String sql = "INSERT INTO [Branch]\n"
                + "(BranchName, Location)\n"
                + "VALUES\n"
                + "(?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, branch.getBranchName());
            stm.setString(2, branch.getLocation());

            stm.executeUpdate();
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Insert FAIL!");
    }

    @Override
    public Response<List<Branch>> all() {
        List<Branch> branchList = new ArrayList<>();
        String sql = "SELECT BranchID, Location, BranchName "
                + " FROM Branch";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Branch r = new Branch();
                r.setBranchID(rs.getInt("BranchID"));
                r.setLocation(rs.getNString("Location"));
                r.setBranchName(rs.getNString("BranchName"));

                branchList.add(r);
            }
            return new Response<>(Response.OK, branchList, "All branchs are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> update(Branch branch) {
        Map<String, Boolean> map = new HashMap<>();
        String sql = "UPDATE [Branch]\n"
                + "SET Location = ?,\n"
                + "    BranchName = ?,\n"
                + "WHERE BranchID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, branch.getLocation());
            stm.setString(2, branch.getBranchName());
            stm.setInt(3, branch.getBranchID());

            stm.executeUpdate();
            map.put("updated", true);
            return new Response<>(Response.OK, map, "Update SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("updated", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Update FAIL!");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Branch branch) {
        Map<String, Boolean> map = new HashMap<>();
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE [Branch] WHERE BranchID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, branch.getBranchID());

            stm.executeUpdate();

            status.put("deleted", true);
            return new Response<>(Response.OK, status, "Delete SUCCESS");
        } catch (SQLException ex) {
            Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        status.put("delete", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Delete fail!");
    }

    @Override
    public Response<Branch> get(Branch model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     public Branch getBranchById(Integer branchId){
        Branch branch = new Branch();
        try (PreparedStatement pstm = connection.prepareCall("select branchId, location, branchName from Branch where branchId = ?")){
            pstm.setInt(1, branchId);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                branch.setBranchID(branchId);
                branch.setLocation(rs.getString("location"));
                branch.setBranchName("branchName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branch;
    }
     
    public Response<Integer> getNumberOfBranches(){
        Integer noOfBranches = 0;
        String sql = "SELECT COUNT(BranchID) AS NoOfBranches\n"
                + " FROM Branch";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                noOfBranches = rs.getInt("NoOfBranches");
            }
            return new Response<>(Response.OK, noOfBranches, "Total branchs are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(BranchDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
}
