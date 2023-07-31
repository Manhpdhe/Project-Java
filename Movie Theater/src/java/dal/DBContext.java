/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Response;

/**
 *
 * @author Admin
 */
public abstract class DBContext<T> {
    protected Connection connection;

    public DBContext() {
        try {
            String username = "sa";
            String password = "12345678";
            //String username = "cinemaproject";
            //String password = "12345";
            
            // Remember to change back to
            // "MSSQLSERVER" after change
            //String hostName = "groovy-cineplex-db.cdtb7v5nxh8r.ap-southeast-1.rds.amazonaws.com";
            String hostName = "localhost\\MSSQLSERVER";
            
            int port = 1433;
            String targetDatabase = "GroovyCineplexDB";
            String url =  String.format("jdbc:sqlserver://%s:%d;databaseName=%s",hostName, port, targetDatabase);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (SQLException e) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }  
        
        
    }
    
    public abstract Response<Map<String,Boolean>> insert(T model);
    
    public abstract Response<T> get(T model);
    
    public abstract Response<Map<String,Boolean>> delete(T model);
    
    public abstract Response<Map<String,Boolean>> update(T model);
    
    public abstract Response<List<T>> all();
    
}
