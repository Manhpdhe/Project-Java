
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
import model.Response;
import model.Room;

/**
 *
 * @author HP
 */
public class RoomDBContext extends DBContext<Room> {

    private final BranchDBContext branchDBContext = new BranchDBContext();

    @Override
    public Response<List<Room>> all() {
        List<Room> roomList = new ArrayList<>();
        String sql = "SELECT r.RoomID, r.RoomName, r.NumRows, r.NumCols, r.BranchID, b.BranchID, b.BranchName, b.[Location]\n"
                + "FROM Room r INNER JOIN Branch b ON r.BranchID = b.BranchID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));
                Branch branch = new Branch();
                branch.setBranchID(rs.getInt("BranchID"));
                branch.setBranchName(rs.getString("BranchName"));
                branch.setLocation(rs.getString("Location"));
                r.setBranch(branch);

                roomList.add(r);

            }
            return new Response<>(Response.OK, roomList, "All rooms are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Room room) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE [Room] WHERE RoomID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, room.getRoomID());

            stm.executeUpdate();
            status.put("processSuccessful", true);

            return new Response<>(Response.OK, status, "Delete successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        status.put("processSuccessful", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Cannot delete room!");
    }

    @Override
    public Response update(Room room) {
        String sql = "UPDATE [Room]\n"
                + "SET RoomName = ?,\n"
                + "NumRows = ?,\n"
                + "NumCols = ?,\n"
                + "BranchID = ?,\n"
                + "WHERE RoomID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, room.getRoomName());
            stm.setInt(2, room.getNumRows());
            stm.setInt(3, room.getNumCols());
            stm.setInt(4, room.getBranch().getBranchID());
            stm.setInt(5, room.getRoomID());

            stm.executeUpdate();
            return new Response(Response.OK, null, "Oke");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response(Response.NOT_FOUND, null, "Not oke");
    }

    @Override
    public Response<Room> get(Room model) {
        String sql = "SELECT r.RoomID, r.RoomName, r.NumRows, r.NumCols, r.BranchID, b.BranchID, b.BranchName, b.[Location]\n"
                + "FROM Room r INNER JOIN Branch b ON r.BranchID = b.BranchID\n"
                + "WHERE r.RoomID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getRoomID());
            rs = stm.executeQuery();
            if (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));
                Branch branch = new Branch();
                branch.setBranchID(rs.getInt("BranchID"));
                branch.setBranchName(rs.getString("BranchName"));
                branch.setLocation(rs.getString("Location"));
                r.setBranch(branch);

                return new Response<>(Response.OK, r, "Room is retrieved successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No room retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> insert(Room room) {
        Map<String, Boolean> map = new HashMap<>();
        String sql = "INSERT INTO [Room]\n"
                + "(RoomName, NumRows, NumCols, BranchID)\n"
                + "VALUES\n"
                + "(?,?,?,?)";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, room.getRoomName());
            stm.setInt(2, room.getNumRows());
            stm.setInt(3, room.getNumCols());
            stm.setInt(4, room.getBranch().getBranchID());

            stm.executeUpdate();
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Success");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        map.put("inserted", false);
        return new Response<>(Response.NOT_FOUND, map, "Fail");
    }

    public Response<List<Room>> getByBranchID(String branchID) {
        List<Room> roomList = new ArrayList<>();
        String sql = "SELECT r.RoomID, r.RoomName, r.NumRows, r.NumCols, r.BranchID, b.BranchName"
                + " FROM Room r, Branch b"
                + " WHERE r.BranchID = b.BranchID"
                + " AND r.BranchID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, branchID);

            rs = stm.executeQuery();
            while (rs.next()) {
                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));
                Branch branch = new Branch();
                branch.setBranchID(rs.getInt("BranchID"));
                branch.setBranchName(rs.getNString("BranchName"));
                r.setBranch(branch);

                roomList.add(r);

            }
            return new Response<>(Response.OK, roomList, "Branch rooms are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    public Room getRoomById(Integer roomId) {
        Room room = new Room();
        try ( PreparedStatement pstm = connection.prepareCall("Select * from Room where roomId = ?")) {
            pstm.setInt(1, roomId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                room.setRoomID(roomId);
                room.setRoomName(rs.getString("roomName"));
                room.setNumCols(rs.getInt("NumCols"));
                room.setNumRows(rs.getInt("NumRows"));
                room.setBranch(branchDBContext.getBranchById(rs.getInt("branchId")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }
     
    public Response<Integer> getNumberOfSeatsInRoom(Integer roomID) {
        //SeatType 1 = normal ; SeatType 2 = VIP
        String sql = "SELECT COUNT(seatID) AS NoOfSeats \n" +
                     " FROM Seat \n" +
                     " WHERE RoomID = ? AND SeatTypeID in (1,2)";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, roomID);
            
            rs = stm.executeQuery();
            
            Integer noOfSeats = 0;
            while(rs.next())
            { 
                noOfSeats = rs.getInt("NoOfSeats");
            }
            return new Response<>(Response.OK, noOfSeats, "Branch rooms are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Integer> getNumberOfRooms() {
        Integer noOfRooms = 0;
        String sql = "SELECT COUNT(roomID) AS NoOfRooms \n" +
                     " FROM Room";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next())
            {
                noOfRooms = rs.getInt("NoOfRooms");
            }
            return new Response<>(Response.OK, noOfRooms, "Total rooms are retrieved successfully");
            
        } catch (SQLException ex) {
            Logger.getLogger(RoomDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

}
