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
import model.Customer;
import model.Response;
import util.BaseDir;

/**
 *
 * @author Admin
 */
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String info = req.getParameter("info");
        CustomerDBContext cusCtx = new CustomerDBContext();
        Response<Customer> cusRes = cusCtx.getByEmailOrAccountName(info);
        if (cusRes.getStatus() == Response.OK) {
            req.getSession().setAttribute("tempCustomer",cusRes.getReturnObject());
            resp.sendRedirect(BaseDir.connect(req, "verify?vertype=2"));
        } else {
            req.setAttribute("errmsg", cusRes.getMessage());
            req.getRequestDispatcher("/views/account/forgot_password_input_email.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.getRequestDispatcher("/views/account/forgot_password_input_email.jsp").forward(req,resp);
    }
    
}
