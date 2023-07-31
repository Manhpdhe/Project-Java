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
import model.SeatType;
import model.TicketPrice;

/**
 *
 * @author Admin
 */
public class TicketPriceDBContext extends DBContext<TicketPrice> {

    @Override
    public Response<Map<String, Boolean>> insert(TicketPrice model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<TicketPrice> get(TicketPrice model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(TicketPrice model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(TicketPrice model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<Map<Integer, TicketPrice>> allRecent() {
        Map<Integer,TicketPrice> priceMap = new HashMap<>();
        String sql = "SELECT t1.TicketPriceID, st.SeatTypeID, st.SeatTypeName, t1.Price, t1.CreateTime FROM TicketPrice t1\n"
                + "JOIN (\n"
                + "    SELECT SeatTypeID, MAX(CreateTime) AS MaxTime\n"
                + "    FROM TicketPrice\n"
                + "    GROUP BY SeatTypeID\n"
                + ") t2\n"
                + "ON t1.SeatTypeID = t2.SeatTypeID AND t1.CreateTime = t2.MaxTime\n"
                + "INNER JOIN SeatType st ON t1.SeatTypeID = st.SeatTypeID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                TicketPrice tp = new TicketPrice();
                tp.setTicketPriceId(rs.getInt("TicketPriceID"));

                SeatType st = new SeatType();
                st.setSeatTypeId(rs.getInt("SeatTypeID"));
                st.setSeatTypeName(rs.getString("SeatTypeName"));

                tp.setSeatType(st);

                tp.setPrice(rs.getLong("Price"));
                tp.setCreateTime(rs.getTimestamp("CreateTime"));

                priceMap.put(st.getSeatTypeId(), tp);

            }
            return new Response<>(Response.OK, priceMap, "All prices are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No map retrieved");
    }

    @Override
    public Response<List<TicketPrice>> all() {
        List<TicketPrice> priceList = new ArrayList<>();
        String sql = "SELECT tp.TicketPriceID, st.SeatTypeID, st.SeatTypeName, tp.Price FROM \n"
                + "TicketPrice tp INNER JOIN SeatType st ON tp.SeatTypeID = st.SeatTypeID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                TicketPrice tp = new TicketPrice();
                tp.setTicketPriceId(rs.getInt("TicketPriceID"));

                SeatType st = new SeatType();
                st.setSeatTypeId(rs.getInt("SeatTypeID"));
                st.setSeatTypeName(rs.getString("SeatTypeName"));

                tp.setSeatType(st);

                tp.setPrice(rs.getLong("Price"));

                priceList.add(tp);

            }
            return new Response<>(Response.OK, priceList, "All prices are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    
    public Response<Map<String,Integer>> getBranchRevenueYearMonth(String branchID) {
        Map<String,Integer> monthRevenue = new HashMap<>();
        String sql = "SELECT DATEPART(year,o.OrderTime) as [Year] ,DATEPART(month, o.OrderTime) AS [Month], SUM(tp.Price) AS TotalRevenue\n" +
                    "FROM [Order] o\n" +
                    "INNER JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "INNER JOIN TicketPrice tp ON t.TicketPriceID = tp.TicketPriceID\n" +
                    "INNER JOIN Seat s ON t.SeatID = s.SeatID\n" +
                    "INNER JOIN Room r ON s.RoomID = r.RoomID\n" +
                    "WHERE r.BranchID = ?\n" +
                    "   AND o.OrderTime >= DATEADD(year, -1, GETDATE())" +
                    "GROUP BY DATEPART(year, o.OrderTime), DATEPART(month, o.OrderTime)\n" +
                    "ORDER BY DATEPART(year, o.OrderTime), DATEPART(month, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, branchID);
            rs = stm.executeQuery();
            while (rs.next()) {
                monthRevenue.put(rs.getInt("Year") + "-" + rs.getInt("Month"), rs.getInt("TotalRevenue"));
            }
            return new Response<>(Response.OK, monthRevenue, "Month revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Map<String,Integer>> getTotalRevenue7Days() {
        Map<String,Integer> totalRevenues = new HashMap<>();
        String sql = "SELECT CONVERT(date, o.OrderTime) as [Date], SUM(tp.Price) AS TotalRevenue\n" +
                    "FROM [Order] o\n" +
                    "INNER JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "INNER JOIN TicketPrice tp ON t.TicketPriceID = tp.TicketPriceID\n" +
                    "WHERE o.OrderTime >= DATEADD(day, -7, GETDATE())\n" +
                    "GROUP BY CONVERT(date, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalRevenues.put(rs.getString("Date"), rs.getInt("TotalRevenue"));
            }
            return new Response<>(Response.OK, totalRevenues, "Total revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Map<String,Integer>> getTotalRevenueYearMonth() {
        Map<String,Integer> monthRevenue = new HashMap<>();
        String sql = "SELECT DATEPART(year,o.OrderTime) as [Year] ,DATEPART(month, o.OrderTime) AS [Month], SUM(tp.Price) AS TotalRevenue\n" +
                    "FROM [Order] o\n" +
                    "INNER JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "INNER JOIN TicketPrice tp ON t.TicketPriceID = tp.TicketPriceID\n" +
                    "WHERE o.OrderTime >= DATEADD(year, -1, GETDATE())" +
                    "GROUP BY DATEPART(year, o.OrderTime), DATEPART(month, o.OrderTime)\n" +
                    "ORDER BY DATEPART(year, o.OrderTime), DATEPART(month, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                monthRevenue.put(rs.getInt("Year") + "-" + rs.getInt("Month"), rs.getInt("TotalRevenue"));
            }
            return new Response<>(Response.OK, monthRevenue, "Month revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
        public Response<Map<String,Integer>> getTotalRevenueYearQuarter() {
        Map<String,Integer> monthRevenue = new HashMap<>();
        String sql = "SELECT\n" +
                    "    CASE\n" +
                    "        WHEN quarter = 1 THEN 'Quarter 1'\n" +
                    "        WHEN quarter = 2 THEN 'Quarter 2'\n" +
                    "        WHEN quarter = 3 THEN 'Quarter 3'\n" +
                    "        WHEN quarter = 4 THEN 'Quarter 4'\n" +
                    "    END AS Quarter,\n" +
                    "    TotalRevenue\n" +
                    "FROM\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            (DATEPART(month, o.OrderTime) - 1) / 3 + 1 AS quarter,\n" +
                    "            SUM(tp.Price) AS TotalRevenue\n" +
                    "        FROM [Order] o\n" +
                    "			INNER JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "			INNER JOIN TicketPrice tp ON t.TicketPriceID = tp.TicketPriceID\n" +
                    "			INNER JOIN Seat s ON t.SeatID = s.SeatID\n" +
                    "			INNER JOIN Room r ON s.RoomID = r.RoomID\n" +
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
                monthRevenue.put(rs.getString("Quarter"), rs.getInt("TotalRevenue"));
            }
            return new Response<>(Response.OK, monthRevenue, "Month revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
        
        public Response<Map<String,Integer>> getTotalRevenue7DaysPrevious() {
        Map<String,Integer> totalRevenues = new HashMap<>();
        String sql = "SELECT CONVERT(date, o.OrderTime) as [Date], SUM(tp.Price) AS TotalRevenue\n" +
                    "FROM [Order] o\n" +
                    "INNER JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "INNER JOIN TicketPrice tp ON t.TicketPriceID = tp.TicketPriceID\n" +
                    "WHERE o.OrderTime between DATEADD(day, -14, GETDATE()) and DATEADD(day, -7, GETDATE())\n" +
                    "GROUP BY CONVERT(date, o.OrderTime)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalRevenues.put(rs.getString("Date"), rs.getInt("TotalRevenue"));
            }
            return new Response<>(Response.OK, totalRevenues, "Total revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
