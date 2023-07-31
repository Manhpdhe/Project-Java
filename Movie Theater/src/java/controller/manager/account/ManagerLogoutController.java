/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.account;

import controller.account.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import util.BaseDir;

/**
 *
 * @author Bui Tien Dung - HE171162
 */
public class ManagerLogoutController extends HttpServlet {
    
    /**
    * author: Tien Dung
    * purpose: Request processor function for both GET and POST request
    * create date: 12-5-2023 (DD-MM-YYYY)
    */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String destination = req.getParameter("destination");
        if (destination == null)
            destination = "home";
        
        req.getSession().setAttribute("manager", null);
        
        // Deleting cookies also
        Cookie[] cookies = req.getCookies();
            
            // If cookies aren't null
        if(cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                String cookieName = cookies[i].getName();
                if (cookieName.equals("gc_mng_username") || cookieName.equals("gc_mng_password")) {
                    Cookie resetCookie = new Cookie(cookieName, "");
                    resetCookie.setMaxAge(0);
                    resp.addCookie(resetCookie);
                }     
                    
            }
        }
        resp.sendRedirect(destination);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
        
    }
    
}
