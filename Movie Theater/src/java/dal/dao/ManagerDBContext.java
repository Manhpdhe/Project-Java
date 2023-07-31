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
import model.Gender;
import model.Manager;
import model.ManagerRole;
import model.Response;

/**
 *
 * @author Admin
 */
public class ManagerDBContext extends DBContext<Manager> {

    @Override
    public Response<Map<String, Boolean>> insert(Manager model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Manager> get(Manager model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Manager model) {
        Map<String,Boolean> status = new HashMap<>();
        String sql = "DELETE [Customer] WHERE CustomerID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            
            stm.setInt(1, model.getManagerId());
            
            stm.executeUpdate();
            status.put("deleted", true);
            
            return new Response<>(Response.OK, status, "Delete SUCCESS");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        status.put("deleted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Delete FAIL");
    }

    @Override
    public Response<Map<String, Boolean>> update(Manager model) {
        Map<String,Boolean> map = new HashMap<>();
        String sql = "UPDATE [Manager]\n" +
                     "SET AccountName = ?,\n" +
                     "	FirstName = ?,\n" +
                     "	LastName = ?,\n" +
                     "	GenderID = ?,\n" +
                     "	Email = ?,\n" +
                     "	DOB = ?,\n" +
                     "	[Address] = ?,\n" +
                     "	PhoneNumber = ?,\n" +
                     "WHERE ManagerID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            
            
            stm.setString(1, model.getAccountName());
            stm.setString(2, model.getFirstName());
            stm.setString(3, model.getLastName());
            stm.setInt(4, model.getGender().getGenderId());
            stm.setString(5, model.getEmail());
            stm.setDate(6, model.getDOB());
            stm.setString(7, model.getAddress());
            stm.setString(8, model.getPhoneNumber());
            stm.setInt(9, model.getManagerId());
            
            stm.executeUpdate();
            map.put("updated", true);
            return new Response<>(Response.OK, map, "Update SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("updated", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, map, "Update FAIL!");
    }

    @Override
    public Response<List<Manager>> all() {
        List<Manager> userList = new ArrayList<>();
        String sql = "SELECT c.ManagerID, c.AccountName, c.FirstName, c.LastName, g.GenderID, g.GenderName, c.[Address], c.PhoneNumber FROM\n" +
                     "Manager c INNER JOIN Gender g ON c.GenderID = g.GenderID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next())
            {
                Manager u = new Manager();
                u.setManagerId(rs.getInt("ManagerID"));
                u.setAccountName(rs.getString("AccountName"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
                
                Gender g = new Gender();
                g.setGenderId(rs.getInt("GenderID"));
                g.setGenderName(rs.getString("GenderName"));
                u.setGender(g);
                
                u.setAddress(rs.getString("Address"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                
                userList.add(u);   
                
            }
            return new Response<>(Response.OK, userList, "All users are retrieved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
    
    public Response<Map<String, Boolean>> insert(Manager model, String password) {
        
        Map<String,Boolean> map = new HashMap<>();
        String accountSql = "INSERT INTO [Manager]\n" +
                     "(AccountName, [Password], FirstName, LastName, GenderID, \n" +
                     "Email, DOB, [Address], PhoneNumber)\n" +
                     "VALUES\n" +
                     "(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = null;
        String query = "{call MyProcedure(?, ?)}";
        PreparedStatement getStm = null;
        ResultSet rs = null;
        try {
            
            stm = connection.prepareStatement(accountSql);
            
            stm.setString(1, model.getAccountName());
            stm.setString(2, password);
            stm.setString(3, model.getFirstName());
            stm.setString(4, model.getLastName());
            stm.setInt(5, model.getGender().getGenderId());
            stm.setString(6, model.getEmail());
            stm.setDate(7, model.getDOB());
            stm.setString(8, model.getAddress());
            stm.setString(9, model.getPhoneNumber());
            
            stm.executeUpdate();
            
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        map.put("inserted", false);
        return new Response<>(Response.OK, map, "Insert FAIL!");
    }
    
    public Response<Manager> get(String accountName, String password) {
        String sql = "SELECT m.ManagerID, m.AccountName, m.FirstName, m.LastName, g.GenderID, g.GenderName, m.[Address], m.PhoneNumber, mr.ManagerRoleID, mr.RoleName\n " +
                    "FROM Manager m\n " +
                    "INNER JOIN Gender g ON m.GenderID = g.GenderID\n " +
                    "INNER JOIN ManagerRole mr ON m.ManagerRoleID = mr.ManagerRoleID\n " +
                    "WHERE m.AccountName = ? AND m.Password = ?\n ";// +
                 //   "     AND m.ManagerRoleID = 2"; // ManagerRoleID = 2 => CinemaManager
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, accountName);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if(rs.next())
            {
                Manager m = new Manager();
                m.setManagerId(rs.getInt("ManagerID"));
                m.setAccountName(rs.getString("AccountName"));
                m.setFirstName(rs.getNString("FirstName"));
                m.setLastName(rs.getNString("LastName"));
                
                Gender g = new Gender();
                g.setGenderId(rs.getInt("GenderID"));
                g.setGenderName(rs.getString("GenderName"));
                m.setGender(g);
                
                m.setAddress(rs.getString("Address"));
                m.setPhoneNumber(rs.getString("PhoneNumber"));
                
                ManagerRole mr = new ManagerRole();
                mr.setManagerRoleId(rs.getInt("ManagerRoleID"));
                mr.setRoleName(rs.getString("RoleName"));
                m.setManagerRole(mr);
                
                return new Response<>(Response.OK, m, "Manager retrieve successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "Tên đăng nhập hoặc mật khẩu không đúng!");
    }
    
    public Response<Map<String,String>> checkUnique(Manager model) {
        // Error mapping object
        Map<String,String> errorMap = new HashMap<>();
        String sql = "SELECT AccountName, Email, PhoneNumber FROM [Manager]\n" +
                     "WHERE AccountName = ? OR Email = ? OR PhoneNumber = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            System.out.println(model.getAccountName());
            stm.setString(1, model.getAccountName());
            System.out.println(model.getEmail());
            stm.setString(2, model.getEmail());
            System.out.println(model.getPhoneNumber());
            stm.setString(3, model.getPhoneNumber());
            rs = stm.executeQuery();
            // At most 3 rows will be returned
            while(rs.next())
            {
                if (model.getAccountName().equals(rs.getString("UserName")))
                    if (!errorMap.containsKey("duplicateUserName"))
                        errorMap.put("duplicateUserName", "Tên đăng nhập này đã tồn tại!");
                if (model.getEmail().equals(rs.getString("Email")))
                    if (!errorMap.containsKey("duplicateEmail"))
                        errorMap.put("duplicateEmail", "Email này đã có tài khoản sử dụng. Vui lòng thử email khác.");
                if (model.getPhoneNumber().equals(rs.getString("PhoneNumber")))
                    if (!errorMap.containsKey("duplicatePhoneNumber"))
                        errorMap.put("duplicatePhoneNumber", "SĐT này đã có tài khoản sử dụng. Vui lòng thử SĐT khác.");
                
            }
            if (!errorMap.isEmpty())
                return new Response<>(Response.INTERNAL_SERVER_ERROR, errorMap, "Registration error!");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ManagerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Response<>(Response.OK, null, "Condition satisfied!");
    }
    
}
