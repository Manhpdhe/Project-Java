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
import model.Branch;
import model.Customer;
import model.Response;
import model.Room;
import model.TicketHistory;

/**
 *
 * @author Admin
 */
public class TicketHistoryDBContext extends DBContext<TicketHistory> {

    @Override
    public Response<Map<String, Boolean>> insert(TicketHistory model) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "INSERT INTO TicketHistory (OrderCode, SeatsString, TimeString, OrderTime, CustomerID, RoomName, BranchName, MovieName, TotalPrice, PaymentStatus, PaymentLink)\n"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setString(1, model.getOrderCode());
            pstm.setString(2, model.getSeatsString());
            pstm.setString(3, model.getTimeString());
            pstm.setTimestamp(4, model.getOrderTime());
            pstm.setInt(5, model.getCustomer().getCustomerId());
            pstm.setString(6, model.getRoomName());
            pstm.setString(7, model.getBranchName());
            pstm.setString(8, model.getMovieName());
            pstm.setLong(9, model.getTotalPrice());
            pstm.setInt(10, model.getPaymentStatus());
            pstm.setString(11, model.getPaymentLink());
            pstm.executeUpdate();
            status.put("processSuccessful", true);
            return new Response<>(Response.OK, status, "create history done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.INTERNAL_SERVER_ERROR, null, "Create history fail");
    }

    @Override
    public Response<TicketHistory> get(TicketHistory model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(TicketHistory model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(TicketHistory model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<Map<String, Boolean>> updateStatus(TicketHistory model) {
        Map<String, Boolean> map = new HashMap<>();
        String sql = "UPDATE TicketHistory\n"
                + "SET\n"
                + "PaymentStatus = ?\n"
                + "WHERE TicketHistoryID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, model.getPaymentStatus());
            stm.setInt(2, model.getTicketHistoryId());

            stm.executeUpdate();

            map.put("updated", true);
            return new Response<>(Response.OK, map, "Update SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("updated", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Update FAIL!");
    }

    @Override
    public Response<List<TicketHistory>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<TicketHistory> byOrderCode(String code) {
        String sql = "SELECT th.TicketHistoryID, th.OrderCode, th.SeatsString, th.TimeString, th.OrderTime, th.CustomerID,\n"
                + "th.MovieName, th.TotalPrice, th.PaymentStatus, th.PaymentLink, th.RoomName, th.BranchName FROM\n"
                + "TicketHistory th INNER JOIN Customer c\n"
                + "ON th.CustomerID = c.CustomerID\n"
                + "WHERE th.OrderCode = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, code);
            rs = stm.executeQuery();
            if (rs.next()) {
                TicketHistory th = new TicketHistory();
                th.setTicketHistoryId(rs.getInt("TicketHistoryID"));
                th.setOrderCode(rs.getString("OrderCode"));
                th.setSeatsString(rs.getString("SeatsString"));
                th.setTimeString(rs.getString("TimeString"));
                th.setOrderTime(rs.getTimestamp("OrderTime"));

                th.setRoomName(rs.getString("RoomName"));
                th.setBranchName(rs.getString("BranchName"));
                th.setMovieName(rs.getString("MovieName"));
                th.setTotalPrice(rs.getLong("TotalPrice"));
                th.setPaymentStatus(rs.getInt("PaymentStatus"));
                th.setPaymentLink(rs.getString("PaymentLink"));

                return new Response<>(Response.OK, th, "Ticket history is retrieved successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    public Response<List<TicketHistory>> byCustomer(Customer cus) {
        List<TicketHistory> historyList = new ArrayList<>();
        String sql = "SELECT th.TicketHistoryID, th.OrderCode, th.SeatsString, th.TimeString, th.OrderTime, th.CustomerID,\n"
                + "th.MovieName, th.TotalPrice, th.PaymentStatus, th.PaymentLink, th.RoomName, th.BranchName FROM\n"
                + "TicketHistory th INNER JOIN Customer c\n"
                + "ON th.CustomerID = c.CustomerID\n"
                + "WHERE th.CustomerID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, cus.getCustomerId());
            rs = stm.executeQuery();
            while (rs.next()) {
                TicketHistory th = new TicketHistory();
                th.setTicketHistoryId(rs.getInt("TicketHistoryID"));
                th.setOrderCode(rs.getString("OrderCode"));
                th.setSeatsString(rs.getString("SeatsString"));
                th.setTimeString(rs.getString("TimeString"));
                th.setOrderTime(rs.getTimestamp("OrderTime"));
                th.setCustomer(cus);

                th.setRoomName(rs.getString("RoomName"));
                th.setBranchName(rs.getString("BranchName"));
                th.setMovieName(rs.getString("MovieName"));
                th.setTotalPrice(rs.getLong("TotalPrice"));
                th.setPaymentStatus(rs.getInt("PaymentStatus"));
                th.setPaymentLink(rs.getString("PaymentLink"));

                historyList.add(th);
            }
            return new Response<>(Response.OK, historyList, "All ticket history are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketHistoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
