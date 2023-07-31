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
import model.Branch;
import model.Order;
import model.Response;
import model.Room;
import model.Seat;
import model.SeatType;

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class SeatDBContext extends DBContext<Seat> {

    @Override
    public Response<Map<String, Boolean>> insert(Seat seat) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "INSERT INTO Seat (RowNo, ColNo, RealColNo, RoomID)\n"
                + "VALUES (?, ?, ?, ?)";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, seat.getRowNo());
            pstm.setInt(2, seat.getColNo());
            pstm.setInt(3, seat.getRealColNo());
            pstm.setInt(4, seat.getRoom().getRoomID());
            pstm.executeUpdate();
            status.put("processSuccessful", true);
            return new Response<>(Response.OK, status, "create seat done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.INTERNAL_SERVER_ERROR, null, "Create seat fail");
    }

    public Response<List<Seat>> getByRoom(Room room) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT s.SeatID, s.RowNo, s.ColNo, s.RealRowNo, s.RealColNo, st.SeatTypeID, st.SeatTypeName,\n"
                + "r.RoomID, r.RoomName, r.NumRows, r.NumCols, b.BranchID, b.BranchName, b.[Location]\n"
                + "FROM Seat s INNER JOIN SeatType st ON s.SeatTypeID = st.SeatTypeID\n"
                + "INNER JOIN Room r ON s.RoomID = r.RoomID \n"
                + "INNER JOIN Branch b ON r.BranchID = b.BranchID\n"
                + "WHERE r.RoomID = ?";
        try ( PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, room.getRoomID());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("SeatID"));
                seat.setRowNo(rs.getInt("RowNo"));
                seat.setColNo(rs.getInt("ColNo"));
                seat.setRealRowNo(rs.getInt("RealRowNo"));
                seat.setRealColNo(rs.getInt("RealColNo"));

                SeatType st = new SeatType();
                st.setSeatTypeId(rs.getInt("SeatTypeID"));
                st.setSeatTypeName(rs.getString("SeatTypeName"));

                seat.setSeatType(st);

                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));

                Branch b = new Branch();
                b.setBranchID(rs.getInt("BranchID"));
                b.setBranchName(rs.getString("BranchName"));
                b.setLocation(rs.getString("Location"));

                r.setBranch(b);
                seat.setRoom(r);

                seats.add(seat);
            }
            return new Response<>(Response.OK, seats, "Get seat done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    @Override
    public Response<Seat> get(Seat seat) {
        ResultSet rs = null;
        String sql = "SELECT s.SeatID, s.RowNo, s.ColNo, s.RealColNo, st.SeatTypeID, st.SeatTypeName,\n"
                + "r.RoomID, r.RoomName, r.NumRows, r.NumCols, b.BranchID, b.BranchName, b.[Location]\n"
                + "FROM Seat s INNER JOIN SeatType st ON s.SeatTypeID = st.SeatTypeID\n"
                + "INNER JOIN Room r ON s.RoomID = r.RoomID \n"
                + "INNER JOIN Branch b ON r.BranchID = b.BranchID\n"
                + "WHERE s.SeatID = ?";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, seat.getSeatId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                Seat s = new Seat();

                s.setSeatId(rs.getInt("SeatID"));
                s.setRowNo(rs.getInt("RowNo"));
                s.setColNo(rs.getInt("ColNo"));
                s.setRealColNo(rs.getInt("RealColNo"));

                SeatType st = new SeatType();
                st.setSeatTypeId(rs.getInt("SeatTypeID"));
                st.setSeatTypeName(rs.getString("SeatTypeName"));

                s.setSeatType(st);

                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));

                Branch b = new Branch();
                b.setBranchID(rs.getInt("BranchID"));
                b.setBranchName(rs.getString("BranchName"));
                b.setLocation(rs.getString("Location"));

                r.setBranch(b);
                s.setRoom(r);

                return new Response<>(Response.OK, s, "Get seat done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    @Override
    public Response<Map<String, Boolean>> delete(Seat seat) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE FROM Seat WHERE SeatID = ?";
        try ( PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, seat.getSeatId());
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
    public Response<Map<String, Boolean>> update(Seat seat) {
        Map<String, Boolean> status = new HashMap<>();
        ResultSet rs = null;
        String sql = "UPDATE Seat SET \n"
                + "RowNo = ?,\n"
                + "ColNo = ?,\n"
                + "RealColNo = ?,\n"
                + "RoomID = ?,\n"
                + "WHERE SeatID = ?";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setInt(1, seat.getRowNo());
            pstm.setInt(2, seat.getColNo());
            pstm.setInt(3, seat.getRealColNo());
            pstm.setInt(4, seat.getRoom().getRoomID());
            pstm.setInt(5, seat.getSeatId());
            pstm.executeUpdate();
            return new Response<>(Response.OK, status, "update seat done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        status.put("updated", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "update seat fail");
    }

    @Override
    public Response<List<Seat>> all() {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT s.SeatID, s.RowNo, s.ColNo, s.RealRowNo, s.RealColNo, st.SeatTypeID, st.SeatTypeName,\n"
                + "r.RoomID, r.RoomName, r.NumRows, r.NumCols, b.BranchID, b.BranchName, b.[Location]\n"
                + "FROM Seat s INNER JOIN SeatType st ON s.SeatTypeID = st.SeatTypeID\n"
                + "INNER JOIN Room r ON s.RoomID = r.RoomID \n"
                + "INNER JOIN Branch b ON r.BranchID = b.BranchID";
        try ( PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("SeatID"));
                seat.setRowNo(rs.getInt("RowNo"));
                seat.setColNo(rs.getInt("ColNo"));
                seat.setRealRowNo(rs.getInt("RealRowNo"));
                seat.setRealColNo(rs.getInt("RealColNo"));

                SeatType st = new SeatType();
                st.setSeatTypeId(rs.getInt("SeatTypeID"));
                st.setSeatTypeName(rs.getString("SeatTypeName"));

                seat.setSeatType(st);

                Room r = new Room();
                r.setRoomID(rs.getInt("RoomID"));
                r.setRoomName(rs.getString("RoomName"));
                r.setNumRows(rs.getInt("NumRows"));
                r.setNumCols(rs.getInt("NumCols"));

                Branch b = new Branch();
                b.setBranchID(rs.getInt("BranchID"));
                b.setBranchName(rs.getString("BranchName"));
                b.setLocation(rs.getString("Location"));

                r.setBranch(b);
                seat.setRoom(r);

                seats.add(seat);
            }
            return new Response<>(Response.OK, seats, "Get seat done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    public Response<Map<String, Boolean>> modifySeatsInRoom(Room r, Map<Seat, Integer> seatOps) {
        Map<String, Boolean> map = new HashMap<>();
        List<PreparedStatement> stms = new ArrayList<>();
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);

            for (Map.Entry<Seat, Integer> entry : seatOps.entrySet()) {
                String seatSql = "";
                PreparedStatement seatStm = null;
                switch (entry.getValue()) {
                    case 0: // INSERT
                    {
                        seatSql = "INSERT INTO\n"
                                + "Seat (RowNo, ColNo, RealRowNo, RealColNo, RoomID, SeatTypeID)\n"
                                + "VALUES (?, ?, ?, ?, ?, ?)";
                        seatStm = connection.prepareStatement(seatSql);
                        seatStm.setInt(1, entry.getKey().getRowNo());
                        seatStm.setInt(2, entry.getKey().getColNo());
                        seatStm.setInt(3, entry.getKey().getRealRowNo());
                        seatStm.setInt(4, entry.getKey().getRealColNo());
                        seatStm.setInt(5, entry.getKey().getRoom().getRoomID());
                        seatStm.setInt(6, entry.getKey().getSeatType().getSeatTypeId());
                        break;
                    }
                    case 1: // UPDATE
                    {
                        seatSql = "UPDATE Seat\n"
                                + "SET \n"
                                + "SeatTypeID = ?,\n"
                                + "RowNo = ?,\n"
                                + "ColNo = ? \n"
                                + "WHERE SeatID = ?";
                        seatStm = connection.prepareStatement(seatSql);
                        seatStm.setInt(1, entry.getKey().getSeatType().getSeatTypeId());
                        seatStm.setInt(2, entry.getKey().getRowNo());
                        seatStm.setInt(3, entry.getKey().getColNo());
                        seatStm.setInt(4, entry.getKey().getSeatId());
                        break;
                    }
                    case 2: // DELETE
                    {
                        seatSql = "DELETE Seat\n"
                                + "WHERE SeatID = ?";
                        seatStm = connection.prepareStatement(seatSql);
                        seatStm.setInt(1, entry.getKey().getSeatId());
                        break;
                    }
                }
                seatStm.executeUpdate();
                stms.add(seatStm);
            }
            connection.commit();
            map.put("modified", true);
            return new Response<>(Response.OK, map, "Modify SUCCESS!");
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(SeatDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(SeatDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SeatDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (PreparedStatement stm : stms) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SeatDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(SeatDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("modified", false);
        return new Response<>(Response.OK, map, "Modify FAIL!");
    }

}
