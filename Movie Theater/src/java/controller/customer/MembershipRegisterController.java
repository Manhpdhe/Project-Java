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
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import model.Customer;
import model.Membership;
import model.Response;
import util.BaseDir;

/**
 *
 * @author Admin
 */
public class MembershipRegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] cardNumChunks = req.getParameterValues("card_number_confirm");
        String fullCard = "";
        for (String chunk : cardNumChunks) {
            fullCard += chunk;
        }
        MembershipDBContext mbCtx = new MembershipDBContext();
        Membership mb = new Membership();
        mb.setCustomer((Customer) req.getSession().getAttribute("customer"));
        mb.setCardNumber(fullCard);
        mb.setRegisterDate(Date.valueOf(LocalDate.now()));
        mb.setPoints(0);
        Response<Map<String, Boolean>> mbRes = mbCtx.insert(mb);
        if (mbRes.getStatus() == Response.OK) {
            resp.sendRedirect(BaseDir.connect(req, "profile/membership"));
        } else {
            req.setAttribute("errmsg","Can't register card at this moment, please try again");
            req.getRequestDispatcher("/views/customer/membership-register.jsp").forward(req,resp);
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/customer/membership-register.jsp").forward(req,resp);
    }
    
}
