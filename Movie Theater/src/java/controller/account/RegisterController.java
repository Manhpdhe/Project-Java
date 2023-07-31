/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.account;

import dal.dao.CustomerDBContext;
import dal.dao.GenderDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import model.Customer;
import model.Gender;
import model.Response;
import util.BaseDir;

/**
 *
 * @author Bui Tien Dung - HE171162
 */
public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // Get all data from the form
        String lastName = req.getParameter("lastname");
        String firstName = req.getParameter("firstname");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        
        // Date section
        Date dob = Date.valueOf(req.getParameter("dob"));
        
        Gender g = new Gender();
        g.setGenderId(Integer.parseInt(req.getParameter("gender")));
        
        String address = req.getParameter("address");
        String phoneNumber = req.getParameter("phonenumber");
        
        // Every registered user is always assigned role user - admin is a special case
        boolean isAdmin = false;
        
        Customer userInfo = new Customer();
        userInfo.setAccountName(userName);
        userInfo.setLastName(lastName);
        userInfo.setFirstName(firstName);
        userInfo.setEmail(email);
        userInfo.setDOB(dob);
        userInfo.setGender(g);
        userInfo.setAddress(address);
        userInfo.setPhoneNumber(phoneNumber);
        
        CustomerDBContext checkContext = new CustomerDBContext();
        
        // check condition
        Response<Map<String,String>> result = checkContext.checkUnique(userInfo);
        System.out.println(result.getStatus());
        System.out.println(result.getReturnObject());
        if (result.getStatus() == Response.OK) {
            CustomerDBContext insertContext = new CustomerDBContext();
            
            
            insertContext.insert(userInfo, password);
            
            CustomerDBContext loginCtx = new CustomerDBContext();
            Response<Customer> tar = loginCtx.get(userInfo.getAccountName(), password);
            
            req.getSession().setAttribute("customer", tar.getReturnObject());
            
            resp.sendRedirect(BaseDir.connect(req,"verify?vertype=1"));
        } else {
            // Return to register page with errors.
            req.setAttribute("errmsgs", result.getReturnObject());
            GenderDBContext genderCtx = new GenderDBContext();
            List<Gender> genderList = genderCtx.all().getReturnObject();
            // Assumes the retrieval goes successfully
            req.setAttribute("genderList", genderList);
            // Keep unsubmitted info for the user registration form
            req.setAttribute("unsubmittedUser", userInfo);
            
            req.getRequestDispatcher("/views/account/register.jsp").forward(req, resp);
        }
        
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Register page redirect
        GenderDBContext genderCtx = new GenderDBContext();
        List<Gender> genderList = genderCtx.all().getReturnObject();
        // Assumes the retrieval goes successfully
        req.setAttribute("genderList", genderList);
        req.getRequestDispatcher("/views/account/register.jsp").forward(req, resp);
    }
    
}
