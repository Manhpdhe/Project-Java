/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Response;
import model.Verification;

/**
 *
 * @author Admin
 */
public class VerificationDBContext extends DBContext<Verification> {

    @Override
    public Response<Map<String, Boolean>> insert(Verification model) {
        String sql = "";
        Map<String, Boolean> map = new HashMap<>();
        if (model.getManager() != null && model.getCustomer() != null) {
            return new Response<>(Response.FORBIDDEN, null, "Invalid operation");
        }
        if (model.getCustomer() != null && model.getManager() == null) {
            sql = "INSERT INTO Verification (CustomerID, Code, CreateTime)\n"
                + "VALUES (?,?,?)";
        }
        

        PreparedStatement stm = null;
        try {
            System.out.println(sql);
            stm = connection.prepareStatement(sql);

            stm.setInt(1, model.getCustomer().getCustomerId());
            stm.setString(2, model.getCode());
            stm.setTimestamp(3, model.getCreateTime());
            
            stm.executeUpdate();
            
            map.put("inserted", false);
            return new Response<>(Response.NOT_FOUND, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Insert FAIL!");
    }

    @Override
    public Response<Verification> get(Verification model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<Verification> getRecentCodeByAccount(Customer customer) {
        String sql = "SELECT v.Code, v.CreateTime FROM \n"
                + "Verification v\n"
                + "JOIN (\n"
                + "	SELECT CustomerID, MAX(CreateTime) AS MaxTime\n"
                + "	FROM Verification\n"
                + "	GROUP BY CustomerID\n"
                + ") t\n"
                + "ON v.CustomerID = t.CustomerID\n"
                + "INNER JOIN Customer c ON v.CustomerID = c.CustomerID\n"
                + "WHERE c.AccountName = ? AND v.CreateTime = MaxTime";

        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, customer.getAccountName());
            rs = stm.executeQuery();
            if (rs.next()) {
                String retrievedCode = rs.getString("Code");
                Verification v = new Verification();
                v.setCode(retrievedCode);
                v.setCreateTime(rs.getTimestamp("CreateTime"));
                return new Response<>(Response.OK, v, "Verification info retrieved successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(VerificationDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No verification info retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Verification model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Verification model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Verification>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
