/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.customer;

import dal.dao.MembershipDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Customer;
import model.Membership;
import model.Response;

/**
 *
 * @author Admin
 */
public class MembershipController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MembershipDBContext mbCtx = new MembershipDBContext();
        Membership m = new Membership();
        Customer cus = (Customer) req.getSession().getAttribute("customer");
        m.setCustomer(cus);
        Response<Membership> mbRes = mbCtx.getByCustomer(m);
        if (mbRes.getStatus() == Response.OK) {
            req.setAttribute("membership", mbRes.getReturnObject());
        }
        req.getRequestDispatcher("/views/customer/membership.jsp").forward(req,resp);
    }
    
}
