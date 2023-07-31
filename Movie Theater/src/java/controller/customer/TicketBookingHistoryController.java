/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.customer;

import dal.dao.TicketHistoryDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Customer;
import model.Response;
import model.TicketHistory;

/**
 *
 * @author Admin
 */
public class TicketBookingHistoryController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/customer/booking-history.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TicketHistoryDBContext thCtx = new TicketHistoryDBContext();
        Customer c = (Customer) req.getSession().getAttribute("customer");
        Response<List<TicketHistory>> thRes = thCtx.byCustomer(c);
        
        if (thRes.getStatus() == Response.OK) {
            req.setAttribute("history", thRes.getReturnObject());
        } else {
            req.setAttribute("errmsg", "Fail to retrieve ticket history!");
        }
        
        req.getRequestDispatcher("/views/customer/booking-history.jsp").forward(req,resp);
    }
    
}
