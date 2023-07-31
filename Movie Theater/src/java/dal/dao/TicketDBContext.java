/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import model.Response;
import model.Seat;
import model.Show;
import model.Ticket;

/**
 *
 * @author Admin
 */
public class TicketDBContext extends DBContext<Ticket> {

    @Override
    public Response<Map<String, Boolean>> insert(Ticket model) {
        Map<String, Boolean> map = new HashMap<>();
        String accountSql = "INSERT INTO Ticket (OrderID, TicketID)\n"
                + "VALUES (?,?)";
        PreparedStatement stm = null;
        PreparedStatement getStm = null;
        ResultSet rs = null;
        try {

            stm = connection.prepareStatement(accountSql);

            stm.setInt(1, model.getOrder().getOrderId());
            //stm.setInt(2, model.getTicket().getTicketId());

            stm.executeUpdate();

            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.OK, map, "Insert FAIL!");
    }

    /*
    public Response<Map<String, Boolean>> insert(List<Ticket> modelList) {
        
        Map<String,Boolean> map = new HashMap<>();
        String accountSql = "INSERT INTO Ticket (OrderID, TicketID)\n" +
                            "VALUES (?,?)";
        PreparedStatement stm = null;
        PreparedStatement getStm = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(accountSql);
            
            stm.setInt(1, model.getOrder().getOrderId());
            stm.setInt(2, model.getTicket().getTicketId());
            
            stm.executeUpdate();
            
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.OK, map, "Insert FAIL!");
    }
    
    
     */
    @Override
    public Response<Ticket> get(Ticket model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<List<Ticket>> byShow(Show show) {
        List<Ticket> ticketList = new ArrayList<>();
        String sql = "SELECT * FROM Ticket t\n" +
"                INNER JOIN [Order] o ON t.OrderID = o.OrderID\n" +
"                INNER JOIN [Customer] c ON o.CustomerID = c.CustomerID\n" +
"                INNER JOIN [TicketPrice] tp ON t.TicketPriceID = tp.TicketPriceID\n" +
"                INNER JOIN [Show] s ON t.ShowID = s.ShowID\n" +
"                INNER JOIN [TimeSlot] ts ON s.TimeSlotID = ts.TimeSlotID\n" +
"                INNER JOIN [Movie] m ON s.MovieID = m.MovieID\n" +
"                INNER JOIN [Room] r ON ts.RoomID = r.RoomID\n" +
"                INNER JOIN [Branch] b ON r.BranchID = b.BranchID\n" +
"                INNER JOIN [Seat] se ON t.SeatID = se.SeatID\n" +
"                WHERE s.ShowID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, show.getShowID());
            rs = stm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setTicketId(rs.getInt("TicketID"));
                /*
                Order o = new Order();
                o.setOrderId(rs.getInt("OrderID"));
                
                t.setOrder(o);
                
                TicketPrice tp = new TicketPrice();
                tp.ge
                
                t.setTicketPrice(tp);

                 */

                Show s = new Show();
                s.setShowID(rs.getInt("ShowID"));

                Seat se = new Seat();
                se.setSeatId(rs.getInt("SeatID"));
                se.setRowNo(rs.getInt("RowNo"));
                se.setRealColNo(rs.getInt("RealColNo"));
                se.setColNo(rs.getInt("ColNo"));

                t.setSeat(se);

                ticketList.add(t);

            }
            return new Response<>(Response.OK, ticketList, "All tickets are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Map<String, Boolean>> delete(Order o) {
        Map<String,Boolean> status = new HashMap<>();
        String sql = "DELETE FROM Ticket WHERE OrderID = (SELECT OrderID FROM [Order] WHERE CustomerID = ? AND OrderTime = ?)";
        try ( PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, o.getCustomer().getCustomerId());
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            pstm.setString(2, timeFormat.format(o.getOrderTime()));
            pstm.executeUpdate();
            status.put("deleted", true);
            return new Response<>(Response.OK, status, "delete seat done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        status.put("deleted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "delete seat fail");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Ticket model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Ticket model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Ticket>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    /**
     * getBranchTicketSale is to get the nearest ticket sales of a branch for a specific time period
     * ex: branchID = 1, periodType = "day", period = "7"
     * => Find the branch ticket sales of branch 1 for the last 7 days
     */
    public Response<Integer> getLatestBranchTicketSale(String branchID, String timePeriodType, int timePeriod) {
        Integer branchTicketSale = 0;
        String sql = "SELECT COUNT(t.TicketID) AS BranchTicketSale\n" +
                    "FROM [Order] o\n" +
                    "JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "JOIN Seat s ON t.SeatID = s.SeatID\n" +
                    "JOIN Room r ON s.RoomID = r.RoomID\n" +
                    "WHERE o.OrderTime >= DATEADD("+ timePeriodType +", ?, GETDATE())\n" +
                    "	AND r.BranchID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, -timePeriod);
            stm.setString(2, branchID);
            rs = stm.executeQuery();
            while (rs.next()) {
                branchTicketSale = rs.getInt("BranchTicketSale");
            }
            return new Response<>(Response.OK, branchTicketSale, "Branch ticket sales are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Integer> getLatestBranchTicketSale(String branchID, String timePeriodType, int timePeriod, int timeBack) {
        Integer totalTicketSale = 0;
        String sql = "SELECT COUNT(t.TicketID) AS BranchTicketSale\n" +
                    "FROM [Order] o\n" +
                    "JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "JOIN Seat s ON t.SeatID = s.SeatID\n" +
                    "JOIN Room r ON s.RoomID = r.RoomID\n" +
                    "WHERE o.OrderTime between DATEADD("+ timePeriodType +", ?, GETDATE()) and DATEADD("+ timePeriodType +", ?, GETDATE())\n" +
                    "	AND r.BranchID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
         
            stm.setInt(1, -(timeBack+timePeriod));
   
            stm.setInt(2, -timeBack);
            stm.setString(3, branchID);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalTicketSale = rs.getInt("BranchTicketSale");
            }
            return new Response<>(Response.OK, totalTicketSale, "Branch ticket sales are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    

    public Response<Integer> getLatestTotalTicketSale(String timePeriodType, int timePeriod) {
        Integer totalTicketSale = 0;
        String sql = "SELECT COUNT(t.TicketID) AS TotalTicketSale\n" +
                    "FROM [Order] o\n" +
                    "JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "JOIN Seat s ON t.SeatID = s.SeatID\n" +
                    "JOIN Room r ON s.RoomID = r.RoomID\n" +
                    "WHERE o.OrderTime >= DATEADD("+ timePeriodType +", ?, GETDATE())";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
     
            stm.setInt(1, -timePeriod);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalTicketSale = rs.getInt("TotalTicketSale");
            }
            return new Response<>(Response.OK, totalTicketSale, "Total ticket sales are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Integer> getLatestTotalTicketSale(String timePeriodType, int timePeriod, int timeBack) {
        Integer totalTicketSale = 0;
        String sql = "SELECT COUNT(t.TicketID) AS TotalTicketSale\n" +
                    "FROM [Order] o\n" +
                    "JOIN Ticket t ON o.OrderID = t.OrderID\n" +
                    "JOIN Seat s ON t.SeatID = s.SeatID\n" +
                    "JOIN Room r ON s.RoomID = r.RoomID\n" +
                    "WHERE o.OrderTime between DATEADD("+ timePeriodType +", ?, GETDATE()) "+
                    "and DATEADD("+ timePeriodType +", ?, GETDATE())";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
        
            stm.setInt(1, -(timeBack + timePeriod));
      
            stm.setInt(2, -timeBack);
            rs = stm.executeQuery();
            while (rs.next()) {
                totalTicketSale = rs.getInt("TotalTicketSale");
            }
            return new Response<>(Response.OK, totalTicketSale, "Total ticket sales are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Map<String,Integer>> getTop5HighestWatchedMovieThisWeek() {
        Map<String,Integer> top5Movies = new HashMap<>();
        String sql = "SELECT TOP 5 m.MovieID, m.Title, COUNT(t.TicketID) AS NoOfTickets\n" +
                    "FROM Movie m\n" +
                    "INNER JOIN Show s ON s.MovieID = m.MovieID\n" +
                    "INNER JOIN Ticket t ON t.ShowID = s.ShowID\n" +
                    "INNER JOIN [Order] o ON o.OrderID = t.OrderID\n" +
                    "WHERE o.OrderTime >= DATEADD(day, -7, GETDATE())\n" +
                    "GROUP BY m.MovieID, m.Title\n" +
                    "ORDER BY COUNT(t.TicketID) DESC";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                top5Movies.put(rs.getString("Title"), rs.getInt("NoOfTickets"));
            }
            return new Response<>(Response.OK, top5Movies, "Total revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Map<String,Integer>> getTop5HighestWatchedMovieThisMonth() {
        Map<String,Integer> top5Movies = new HashMap<>();
        String sql = "SELECT TOP 5 m.MovieID, m.Title, COUNT(t.TicketID) AS NoOfTickets\n" +
                    "FROM Movie m\n" +
                    "INNER JOIN Show s ON s.MovieID = m.MovieID\n" +
                    "INNER JOIN Ticket t ON t.ShowID = s.ShowID\n" +
                    "INNER JOIN [Order] o ON o.OrderID = t.OrderID\n" +
                    "WHERE o.OrderTime >= DATEADD(month, -1, GETDATE())\n" +
                    "GROUP BY m.MovieID, m.Title\n" +
                    "ORDER BY COUNT(t.TicketID) DESC";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                top5Movies.put(rs.getString("Title"), rs.getInt("NoOfTickets"));
            }
            return new Response<>(Response.OK, top5Movies, "Total revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
        
    public Response<Map<String,Integer>> getTop5HighestWatchedMovieThisQuarter() {
        Map<String,Integer> top5Movies = new HashMap<>();
        String sql = "SELECT TOP 5 m.MovieID, m.Title, COUNT(t.TicketID) AS NoOfTickets\n" +
                    "FROM Movie m\n" +
                    "INNER JOIN Show s ON s.MovieID = m.MovieID\n" +
                    "INNER JOIN Ticket t ON t.ShowID = s.ShowID\n" +
                    "INNER JOIN [Order] o ON o.OrderID = t.OrderID\n" +
                    "WHERE o.OrderTime >= DATEADD(year, -1, GETDATE())\n" +
                    "AND ((DATEPART(month, o.OrderTime) - 1) / 3 + 1) = ((DATEPART(month, GETDATE()) - 1) / 3 + 1)\n" +
                    "GROUP BY m.MovieID, m.Title\n" +
                    "ORDER BY COUNT(t.TicketID) DESC";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                top5Movies.put(rs.getString("Title"), rs.getInt("NoOfTickets"));
            }
            return new Response<>(Response.OK, top5Movies, "Total revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
            
    public Response<Map<String,Integer>> getTop5HighestWatchedMovieThisYear() {
        Map<String,Integer> top5Movies = new HashMap<>();
        String sql = "SELECT TOP 5 m.MovieID, m.Title, COUNT(t.TicketID) AS NoOfTickets\n" +
                    "FROM Movie m\n" +
                    "INNER JOIN Show s ON s.MovieID = m.MovieID\n" +
                    "INNER JOIN Ticket t ON t.ShowID = s.ShowID\n" +
                    "INNER JOIN [Order] o ON o.OrderID = t.OrderID\n" +
                    "WHERE o.OrderTime >= DATEADD(year, -1, GETDATE())\n" +
                    "GROUP BY m.MovieID, m.Title\n" +
                    "ORDER BY COUNT(t.TicketID) DESC";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                top5Movies.put(rs.getString("Title"), rs.getInt("NoOfTickets"));
            }
            return new Response<>(Response.OK, top5Movies, "Total revenues are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(TicketPriceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
