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
import model.Membership;
import model.Response;

/**
 *
 * @author Admin
 */
public class MembershipDBContext extends DBContext<Membership> {

    @Override
    public Response<Map<String, Boolean>> insert(Membership model) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "INSERT INTO Membership (CardNumber, CustomerID, RegisterDate, Points)\n"
                + "VALUES (? ,?, ?, ?)";
        try ( PreparedStatement pstm = connection.prepareCall(sql)) {
            pstm.setString(1, model.getCardNumber());
            pstm.setInt(2, model.getCustomer().getCustomerId());
            pstm.setDate(3, model.getRegisterDate());
            pstm.setLong(4, model.getPoints());
            pstm.executeUpdate();
            status.put("processSuccessful", true);
            return new Response<>(Response.OK, status, "create membership done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response<>(Response.INTERNAL_SERVER_ERROR, null, "Create membership fail");
    }

    public Response<Membership> getByCustomer(Membership model) {
        String sql = "SELECT m.MembershipID, m.CardNumber, m.RegisterDate, m.CustomerID, c.FirstName, c.LastName, m.Points \n"
                + "FROM Membership m INNER JOIN Customer c ON m.CustomerID = c.CustomerID\n"
                + "WHERE m.CustomerID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getCustomer().getCustomerId());
            rs = stm.executeQuery();
            if (rs.next()) {
                Membership m = new Membership();
                m.setMembershipId(rs.getInt("MembershipID"));
                m.setCardNumber(rs.getString("CardNumber"));
                m.setRegisterDate(rs.getDate("RegisterDate"));
                m.setPoints(rs.getLong("Points"));
                
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("CustomerID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                m.setCustomer(c);

                return new Response<>(Response.OK, m, "Membership is retrieved successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MembershipDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No membership retrieved");
    }

    @Override
    public Response<Membership> get(Membership model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Membership model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> update(Membership model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<List<Membership>> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
