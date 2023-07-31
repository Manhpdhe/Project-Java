/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.account;

import dal.dao.CustomerDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import model.Customer;
import model.Response;

/**
 *
 * @author Admin
 */
public class RecoverController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newPass = req.getParameter("newPass");
        String confPass = req.getParameter("confPass");
        Customer c = (Customer) req.getSession().getAttribute("tempCustomer");
        CustomerDBContext cusCtx = new CustomerDBContext();
        Response<Map<String,Boolean>> cusRes = cusCtx.updatePassword(c, newPass);
        if (!newPass.equals(confPass)) {
            req.setAttribute("errmsg", "Confirm password mismtach the new password!");
            req.getRequestDispatcher("/views/account/forgot_password_confirm.jsp").forward(req,resp);
        }
        if (cusRes.getStatus() == Response.OK) {
            req.getRequestDispatcher("/views/account/forgot_password_complete.jsp").forward(req,resp);
        }
        else {
            req.setAttribute("errmsg", "Can't change password, please try again!");
            req.getRequestDispatcher("/views/account/forgot_password_confirm.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customer");
        String submittedOtp = req.getParameter("otp");
        

        req.getRequestDispatcher("/views/account/forgot_password_confirm.jsp").forward(req,resp);
        
        
    }
    
}
