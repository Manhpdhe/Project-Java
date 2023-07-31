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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.Gender;
import model.Response;
import model.Verification;

/**
 *
 * @author Admin
 */
public class CustomerDBContext extends DBContext<Customer> {

    @Override
    public Response<Map<String, Boolean>> insert(Customer model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Response<Map<String, Boolean>> insertWithVerification(Customer model, String password, Verification ver) {
        Map<String, Boolean> map = new HashMap<>();
        List<PreparedStatement> stms = new ArrayList<>();

        PreparedStatement getStm = null;
        ResultSet rs = null;
        try {
            connection.setAutoCommit(false);
            String accountSql = "INSERT INTO [Customer]\n"
                    + "(AccountName, [Password], FirstName, LastName, GenderID, \n"
                    + "Email, DOB, [Address], PhoneNumber, Verified)\n"
                    + "VALUES\n"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement cusStm = null;
            cusStm = connection.prepareStatement(accountSql);

            cusStm.setString(1, model.getAccountName());
            cusStm.setString(2, password);
            cusStm.setString(3, model.getFirstName());
            cusStm.setString(4, model.getLastName());
            cusStm.setInt(5, model.getGender().getGenderId());
            cusStm.setString(6, model.getEmail());
            cusStm.setDate(7, model.getDOB());
            cusStm.setString(8, model.getAddress());
            cusStm.setString(9, model.getPhoneNumber());
            cusStm.setBoolean(10, model.isVerified());

            cusStm.executeUpdate();
            connection.commit();
            stms.add(cusStm);
            String verificationSql = "INSERT INTO Verification (CustomerID, Code, CreateTime) \n"
                    + "values \n"
                    + "((SELECT CustomerID FROM Customer WHERE AccountName = ? AND [Password] = ?),?,?)";

            PreparedStatement verStm = null;
            verStm = connection.prepareStatement(verificationSql);
            verStm.setString(1, model.getAccountName());
            verStm.setString(2, password);
            verStm.setString(3, ver.getCode());
            verStm.setTimestamp(4, ver.getCreateTime());
            verStm.executeUpdate();
            connection.commit();
            stms.add(verStm);
            map.put("inserted", true);
            return new Response<>(Response.OK, map, "Insert SUCCESS!");
        } catch (SQLException ex) {
            try {
                connection.rollback();
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
    public Response<Customer> get(Customer model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Response<Map<String, Boolean>> delete(Customer model) {
        Map<String, Boolean> status = new HashMap<>();
        String sql = "DELETE [Customer] WHERE CustomerID = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setInt(1, model.getCustomerId());

            stm.executeUpdate();
            status.put("deleted", true);

            return new Response<>(Response.OK, status, "Delete SUCCESS");
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
        status.put("deleted", false);
        return new Response<>(Response.INTERNAL_SERVER_ERROR, status, "Delete FAIL");
    }

    @Override
    public Response<Map<String, Boolean>> update(Customer model) {
        Map<String, Boolean> map = new HashMap<>();
        String sql = "UPDATE [Customer]\n"
                + "SET AccountName = ?,\n"
                + "	FirstName = ?,\n"
                + "	LastName = ?,\n"
                + "	GenderID = ?,\n"
                + "	Email = ?,\n"
                + "	DOB = ?,\n"
                + "	[Address] = ?,\n"
                + "	PhoneNumber = ?\n"
                + "WHERE CustomerID = ?";
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
            stm.setInt(9, model.getCustomerId());

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
    public Response<List<Customer>> all() {
        List<Customer> userList = new ArrayList<>();
        String sql = "SELECT c.CustomerID, c.AccountName, c.FirstName, c.LastName, g.GenderID, g.GenderName, c.[Address], c.PhoneNumber FROM\n"
                + "Customer c INNER JOIN Gender g ON c.GenderID = g.GenderID";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Customer u = new Customer();
                u.setCustomerId(rs.getInt("CustomerID"));
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
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }

    public Response<Map<String, Boolean>> insert(Customer model, String password) {

        Map<String, Boolean> map = new HashMap<>();
        String accountSql = "INSERT INTO [Customer]\n"
                + "(AccountName, [Password], FirstName, LastName, GenderID, \n"
                + "Email, DOB, [Address], PhoneNumber)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?,?,?)";
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
        map.put("inserted", false);
        return new Response<>(Response.OK, map, "Insert FAIL!");
    }

    public Response<Customer> get(String accountName, String password) {
        String sql = "SELECT c.CustomerID, c.AccountName, c.FirstName, c.LastName, g.GenderID, g.GenderName, c.DOB, c.Email, c.[Address], c.PhoneNumber, c.Verified FROM \n"
                + "Customer c INNER JOIN Gender g ON c.GenderID = g.GenderID\n"
                + "WHERE c.AccountName = ? AND c.Password = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, accountName);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                Customer u = new Customer();
                u.setCustomerId(rs.getInt("CustomerID"));
                u.setAccountName(rs.getString("AccountName"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));

                Gender g = new Gender();
                g.setGenderId(rs.getInt("GenderID"));
                g.setGenderName(rs.getString("GenderName"));
                u.setGender(g);

                u.setDOB(rs.getDate("DOB"));
                u.setEmail(rs.getString("Email"));
                u.setAddress(rs.getString("Address"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setVerified(rs.getBoolean("Verified"));
                return new Response<>(Response.OK, u, "User retrieve successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        return new Response<>(Response.NOT_FOUND, null, "Tên đăng nhập hoặc mật khẩu không đúng!");
    }
    
    public Response<Customer> getByEmailOrAccountName(String context) {
        String sql = "SELECT c.CustomerID, c.AccountName, c.FirstName, c.LastName, g.GenderID, g.GenderName, c.DOB, c.Email, c.[Address], c.PhoneNumber, c.Verified FROM \n"
                + "Customer c INNER JOIN Gender g ON c.GenderID = g.GenderID\n"
                + "WHERE c.AccountName = ? OR c.Email = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, context);
            stm.setString(2, context);
            rs = stm.executeQuery();
            if (rs.next()) {
                Customer u = new Customer();
                u.setCustomerId(rs.getInt("CustomerID"));
                u.setAccountName(rs.getString("AccountName"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));

                Gender g = new Gender();
                g.setGenderId(rs.getInt("GenderID"));
                g.setGenderName(rs.getString("GenderName"));
                u.setGender(g);
                
                u.setDOB(rs.getDate("DOB"));
                u.setEmail(rs.getString("Email"));
                u.setAddress(rs.getString("Address"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setVerified(rs.getBoolean("Verified"));
                return new Response<>(Response.OK, u, "Tài khoản đã được tìm thấy!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        return new Response<>(Response.NOT_FOUND, null, "Không thể tìm thấy tài khoản theo yêu cầu!");
    }
    
    public Response<Map<String,Boolean>> updatePassword(Customer model, String password) {
        Map<String, Boolean> map = new HashMap<>();
        String sql = "UPDATE [Customer]\n"
                + "SET Password = ? \n"
                + "WHERE AccountName = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setString(1, password);
            stm.setString(2, model.getAccountName());

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

    public Response<Map<String, String>> checkUnique(Customer model) {
        // Error mapping object
        Map<String, String> errorMap = new HashMap<>();
        String sql = "SELECT AccountName, Email, PhoneNumber FROM [Customer]\n"
                + "WHERE AccountName = ? OR Email = ? OR PhoneNumber = ?";
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
            while (rs.next()) {
                if (model.getAccountName().equals(rs.getString("AccountName"))) {
                    if (!errorMap.containsKey("duplicateUserName")) {
                        errorMap.put("duplicateUserName", "Tên đăng nhập này đã tồn tại!");
                    }
                }
                if (model.getEmail().equals(rs.getString("Email"))) {
                    if (!errorMap.containsKey("duplicateEmail")) {
                        errorMap.put("duplicateEmail", "Email này đã có tài khoản sử dụng. Vui lòng thử email khác.");
                    }
                }
                if (model.getPhoneNumber().equals(rs.getString("PhoneNumber"))) {
                    if (!errorMap.containsKey("duplicatePhoneNumber")) {
                        errorMap.put("duplicatePhoneNumber", "SĐT này đã có tài khoản sử dụng. Vui lòng thử SĐT khác.");
                    }
                }

            }
            if (!errorMap.isEmpty()) {
                return new Response<>(Response.INTERNAL_SERVER_ERROR, errorMap, "Registration error!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        return new Response<>(Response.OK, null, "Condition satisfied!");
    }
    
    public Response<Map<String,Boolean>> confirmVerification(Customer model) {
         Map<String, Boolean> map = new HashMap<>();
        String sql = "UPDATE [Customer]\n"
                + "SET Verified = ? \n"
                + "WHERE AccountName = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);

            stm.setBoolean(1, true);
            stm.setString(2, model.getAccountName());

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
    
    public Response<Integer> getNumberOfCustomers() {
        Integer noOfCustomers = 0;
        String sql = "SELECT COUNT(CustomerID) AS NoOfCustomers \n" +
                     " FROM Customer";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next())
            {
                noOfCustomers = rs.getInt("NoOfCustomers");
            }
            return new Response<>(Response.OK, noOfCustomers, "Total rooms are retrieved successfully");
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Response<>(Response.NOT_FOUND, null, "No list retrieved");
    }
}
