/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.account;

import dal.dao.CustomerDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Customer;
import model.Response;
import util.BaseDir;
/**
 *
 * @author Bui Tien Dung - HE171162
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Get all data from the form
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        
        // null - false , not null - true
        boolean rememberChecked = (req.getParameter("rememberuser") == null) ? false : true;
        // HIDDEN - Associated link
        String destination = req.getParameter("destination");
        
        CustomerDBContext cusCtx = new CustomerDBContext();
        Response<Customer> cusRes = cusCtx.get(userName, password);
        
        if (cusRes.getStatus() == Response.OK) {
            Customer retrievedCus = cusRes.getReturnObject();
            // Redirect to destination
            req.getSession().setAttribute("customer", retrievedCus);
            
            // Save cookies
            if (rememberChecked) {
                Cookie userCookie = new Cookie("gc_username",userName);
                Cookie passCookie = new Cookie("gc_password",password);
                
                resp.addCookie(userCookie);
                resp.addCookie(passCookie);
            }
            if (retrievedCus.isVerified()) {
                Map<String,String[]> params = new HashMap<>(req.getParameterMap());
                params.remove("username");
                params.remove("password");
                params.remove("destination");
                params.remove("rememberuser");
                resp.sendRedirect(BaseDir.connect(req, destination, params));
            }
            else {
                resp.sendRedirect(BaseDir.connect(req, "verify?vertype=1"));
            }
            
        } else {
            // set destination
            req.setAttribute("destination", destination);
            // Go to associated page
            Map<String,String[]> params = new HashMap<>(req.getParameterMap());
                params.remove("username");
                params.remove("password");
                params.remove("destination");
                params.remove("rememberuser");
            req.setAttribute("obtainedParams",params);
            req.setAttribute("errmsg",cusRes.getMessage());
            req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (req.getSession().getAttribute("customer") != null) {
            resp.sendRedirect("profile");
        } else {
            // Get destination
        String destination = req.getParameter("destination");
        
        if (destination == null) {
            destination = "home";
        }
           
        req.setAttribute("destination", destination);
        // Go to login page
        req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
        }
                 
    }
    
}
