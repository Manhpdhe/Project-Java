/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import dal.dao.CustomerDBContext;
import dal.dao.ManagerDBContext;
import model.Customer;
import model.Manager;
import model.Response;
import util.BaseDir;

/**
 *
 * @author Admin
 */
public class AuthDataListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequestListener.super.requestDestroyed(sre); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    // The request will initialize both manager and customer accounts!
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
        Customer customer = (Customer) req.getSession().getAttribute("customer");
        Manager manager = (Manager) req.getSession().getAttribute("manager");
        
        
        if (customer == null) {
            Map<String,String> authMap = new HashMap<>();
            Cookie[] cookies = req.getCookies();
            
            // If cookies aren't null
            if(cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    String cookieName = cookies[i].getName();
                    String cookieValue = cookies[i].getValue();
                    authMap.put(cookieName,cookieValue);
                }
                
                
                // Check if fields are sufficient
                if (authMap.containsKey("gc_username") && authMap.containsKey("gc_password")) {
                    // Automatically logins
                    String userName = authMap.get("gc_username");
                    String password = authMap.get("gc_password");
                    CustomerDBContext cusCtx = new CustomerDBContext();
                    Response<Customer> cusRes = cusCtx.get(userName, password);
                    
                    // If response returns the value successfully
                    if (cusRes.getStatus() == Response.OK) {
                        // Check if customer account is verified
                        Customer valid = cusRes.getReturnObject();
                        if (valid.isVerified()) {
                            // Save the retrieved user to Session
                            req.getSession().setAttribute("customer",cusRes.getReturnObject());
                        }
                    }
                }
            }        
            
        }
        if (manager == null) {
            Map<String,String> authMap = new HashMap<>();
            Cookie[] cookies = req.getCookies();
            
            // If cookies aren't null
            if(cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    String cookieName = cookies[i].getName();
                    String cookieValue = cookies[i].getValue();
                    authMap.put(cookieName,cookieValue);
                }
                
                
                // Check if fields are sufficient
                if (authMap.containsKey("gc_mng_username") && authMap.containsKey("gc_mng_password")) {
                    // Automatically logins
                    String userName = authMap.get("gc_mng_username");
                    String password = authMap.get("gc_mng_password");
                    ManagerDBContext mngCtx = new ManagerDBContext();
                    Response<Manager> mngRes = mngCtx.get(userName, password);
                    
                    // If response returns the value successfully
                    if (mngRes.getStatus() == Response.OK) {
                        // Save the retrieved user to Session
                        req.getSession().setAttribute("manager",mngRes.getReturnObject());
                    }
                }
            }        
            
        }
    }
    
}
