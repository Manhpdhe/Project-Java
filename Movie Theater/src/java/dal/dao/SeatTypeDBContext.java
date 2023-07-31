package dal.dao;


import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import model.Response;
import model.SeatType;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class SeatTypeDBContext extends DBContext<SeatType>{

    @Override
    public Response<Map<String, Boolean>> insert(SeatType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<SeatType> get(SeatType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(SeatType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(SeatType model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<SeatType>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public SeatType getSeatTypeById(Integer seatTypeId) {
        SeatType seatType = new SeatType();
        try (PreparedStatement pstm = connection.prepareCall("Select * from SeatType where seatTypeID = ?")){
            pstm.setInt(1, seatTypeId);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                seatType.setSeatTypeId(rs.getInt("seatTypeID"));
                seatType.setSeatTypeName(rs.getString("seatTypeName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seatType;
    }
    
}
