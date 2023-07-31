/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.account;

import dal.dao.ManagerDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Manager;
import model.Response;
/**
 *
 * @author Bui Tien Dung - HE171162
 */
public class ManagerLoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Get all data from the form
        String accountName = req.getParameter("username");
        String password = req.getParameter("password");
        
        // null - false , not null - true
        boolean rememberChecked = (req.getParameter("rememberuser") == null) ? false : true;
        // HIDDEN - Associated link
        String destination = req.getParameter("destination");
        
        ManagerDBContext mngCtx = new ManagerDBContext();
        Response<Manager> mngRes = mngCtx.get(accountName, password);
        if (mngRes.getStatus() == Response.OK) {
            // Redirect to destination
            req.getSession().setAttribute("manager", mngRes.getReturnObject());
            
            // Save cookies
            if (rememberChecked) {
                Cookie userCookie = new Cookie("gc_username",accountName);
                Cookie passCookie = new Cookie("gc_password",password);
                
                resp.addCookie(userCookie);
                resp.addCookie(passCookie);
            }
            resp.sendRedirect(destination);
        } else {
            // set destination
            req.setAttribute("destination", destination);
            // Go to associated page
            req.setAttribute("errmsg",mngRes.getMessage());
            req.getRequestDispatcher("/views/manager/account/login.jsp").forward(req, resp);
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (req.getSession().getAttribute("manager") != null) {
            resp.sendRedirect("managerprofile");
        } else {
            // Get destination
        String destination = req.getParameter("destination");
        
        if (destination == null) {
            destination = "home";
        }
           
        req.setAttribute("destination", destination);
        // Go to login page
        req.getRequestDispatcher("/views/manager/account/login.jsp").forward(req, resp);
        }
                 
    }
    
}
