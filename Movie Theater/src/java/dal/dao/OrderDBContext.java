/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import model.Response;
import model.Ticket;

/**
 *
 * @author Admin
 */
public class OrderDBContext extends DBContext<Order> {

    public Response<Map<String, Boolean>> insertWithTickets(Order order, List<Ticket> tickets) {
        Map<String, Boolean> map = new HashMap<>();
        String orderSql = "INSERT INTO [Order] (CustomerID, OrderTime)\n"
                + "VALUES (?,?)";
        System.out.println(orderSql);
        List<PreparedStatement> stms = new ArrayList<>();
        ResultSet rs = null;
        Savepoint beforeOrder = null;
        try {
            connection.setSavepoint("beforeOrder");
            connection.setAutoCommit(false);
            beforeOrder = connection.setSavepoint("beforeOrder");
            // First, execute the order
            
            PreparedStatement stm = connection.prepareStatement(orderSql);
            stm.setInt(1, order.getCustomer().getCustomerId());
            stm.setTimestamp(2, order.getOrderTime());
            stm.executeUpdate();
            stms.add(stm);
            
            for (Ticket t : tickets) {
                String ticketSql = "INSERT INTO [Ticket]\n"
                        + "VALUES\n"
                        + "((SELECT OrderID FROM [Order] WHERE CustomerID = ? AND OrderTime = ?), ?, ?, ?)";
                System.out.println(ticketSql);
                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                PreparedStatement ticketStm = connection.prepareStatement(ticketSql);
                ticketStm.setInt(1, order.getCustomer().getCustomerId());
                System.out.println(timeFormat.format(order.getOrderTime()));
                ticketStm.setString(2, timeFormat.format(order.getOrderTime()));
                System.out.println(order.getOrderTime());
                ticketStm.setInt(3, t.getTicketPrice().getTicketPriceId());
                ticketStm.setInt(4, t.getShow().getShowID());
                ticketStm.setInt(5, t.getSeat().getSeatId());
                ticketStm.executeUpdate();
                stms.add(ticketStm);
            }
            connection.commit();
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
             try {
                connection.rollback(beforeOrder);
            } catch (SQLException ex1) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (PreparedStatement stm : stms) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Insert FAIL!");
    }

    @Override
    public Response<Map<String, Boolean>> insert(Order model) {
        Map<String, Boolean> map = new HashMap<>();
        String accountSql = "INSERT INTO [Order] (CustomerID, OrderTime)\n"
                + "VALUES (?,?)";
        PreparedStatement stm = null;
        PreparedStatement getStm = null;
        ResultSet rs = null;
        try {

            stm = connection.prepareStatement(accountSql);

            stm.setInt(1, model.getCustomer().getCustomerId());
            stm.setTimestamp(2, model.getOrderTime());

            stm.executeUpdate();

            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Insert FAIL!");
    }

    @Override
    public Response<Order> get(Order model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Order model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Order model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Order>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Response<Map<String,Integer>> getTotalBuyer7Days() {
        Map<String,Integer> totalBuyers = new HashMap<>();
        String sql = "SELECT CONVERT(date, o.OrderTime) as [Date], COUNT(DISTINCT CustomerID) AS totalBuyers\n" +
                    "FROM [Order] o\n" +
                    "WHERE o.OrderTime >= DATEADD(day, -7, GETDATE())\n" +
                    "GROUP BY CONVERT(date, o.OrderTime)\n" +
                    "ORDER BY CONVERT(date, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalBuyers.put(rs.getString("Date"), rs.getInt("totalBuyers"));
            }
            return new Response<>(Response.OK, totalBuyers, "Total buyers are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
        public Response<Map<String,Integer>> getTotalBuyerYearMonth() {
        Map<String,Integer> totalBuyers = new HashMap<>();
        String sql = "SELECT DATEPART(year,o.OrderTime) as [Year] ,DATEPART(month, o.OrderTime) AS [Month], COUNT(DISTINCT CustomerID) AS totalBuyers\n" +
                    "FROM [Order] o\n" +
                    "WHERE o.OrderTime >= DATEADD(year, -1, GETDATE())\n" +
                    "GROUP BY DATEPART(year, o.OrderTime), DATEPART(month, o.OrderTime)\n" +
                    "ORDER BY DATEPART(year, o.OrderTime), DATEPART(month, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalBuyers.put(rs.getInt("Year") + "-" + rs.getInt("Month"), rs.getInt("totalBuyers"));
            }
            return new Response<>(Response.OK, totalBuyers, "Total buyers are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
        
        public Response<Map<String,Integer>> getTotalBuyerYearQuarter() {
        Map<String,Integer> totalBuyers = new HashMap<>();
        String sql = "SELECT\n" +
                    "    CASE\n" +
                    "        WHEN quarter = 1 THEN 'Quarter 1'\n" +
                    "        WHEN quarter = 2 THEN 'Quarter 2'\n" +
                    "        WHEN quarter = 3 THEN 'Quarter 3'\n" +
                    "        WHEN quarter = 4 THEN 'Quarter 4'\n" +
                    "    END AS Quarter,\n" +
                    "    totalBuyers\n" +
                    "FROM\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            (DATEPART(month, o.OrderTime) - 1) / 3 + 1 AS quarter,\n" +
                    "            COUNT(DISTINCT CustomerID) AS totalBuyers\n" +
                    "        FROM\n" +
                    "            [Order] o\n" +
                    "        WHERE\n" +
                    "            o.OrderTime >= DATEADD(year, -1, GETDATE())\n" +
                    "        GROUP BY\n" +
                    "            (DATEPART(month, o.OrderTime) - 1) / 3 + 1\n" +
                    "    ) subquery;";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalBuyers.put(rs.getString("Quarter"), rs.getInt("totalBuyers"));
            }
            return new Response<>(Response.OK, totalBuyers, "Total buyers are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
        
        public Response<Map<String,Integer>> getTotalBuyer7DaysPrevious() {
        Map<String,Integer> totalBuyers = new HashMap<>();
        String sql = "SELECT CONVERT(date, o.OrderTime) as [Date], COUNT(DISTINCT CustomerID) AS totalBuyers\n" +
                    "FROM [Order] o\n" +
                    "WHERE o.OrderTime between DATEADD(day, -14, GETDATE()) and DATEADD(day, -7, GETDATE())\n" +
                    "GROUP BY CONVERT(date, o.OrderTime)\n" +
                    "ORDER BY CONVERT(date, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalBuyers.put(rs.getString("Date"), rs.getInt("totalBuyers"));
            }
            return new Response<>(Response.OK, totalBuyers, "Total buyers are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
