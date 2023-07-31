/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.customer;

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

/**
 *
 * @author Admin
 */
public class ProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get all data from the form
        int id = Integer.parseInt(req.getParameter("id"));
        String lastName = req.getParameter("lname");
        String firstName = req.getParameter("fname");
        String userName = req.getParameter("inpUser");
        //String password = req.getParameter("password");
        String email = req.getParameter("inpEm");

        Gender g = new Gender();
        g.setGenderId(Integer.parseInt(req.getParameter("gender")));

        // Date section
        Date dob = Date.valueOf(req.getParameter("inpDob"));

        String address = req.getParameter("inpAdd");
        String phoneNumber = req.getParameter("inpPhone");

        boolean check = (boolean) ((req.getParameter("checkChange") == null) ? false : true);
        String oldPass = req.getParameter("inpOldPass");
        String newPass = req.getParameter("inpNewPass");

        Customer userInfo = new Customer();
        userInfo.setCustomerId(id);
        userInfo.setAccountName(userName);
        userInfo.setLastName(lastName);
        userInfo.setFirstName(firstName);
        userInfo.setEmail(email);
        userInfo.setDOB(dob);
        userInfo.setGender(g);
        userInfo.setAddress(address);
        userInfo.setPhoneNumber(phoneNumber);
        userInfo.setVerified(true);

        CustomerDBContext checkContext = new CustomerDBContext();

        // check condition
        Response<Map<String, Boolean>> result = checkContext.update(userInfo);
        if (result.getStatus() == Response.OK) {
            req.getSession().setAttribute("customer", userInfo);
            if (check) {
                CustomerDBContext usrCheck = new CustomerDBContext();
                Response<Customer> usr = usrCheck.get(userName, oldPass);
                if (usr.getStatus() != Response.OK) {
                     req.setAttribute("errmsg", "Old password is incorrect!");
                } else {
                    CustomerDBContext changePassCtx = new CustomerDBContext();
                    Response<Map<String, Boolean>> passResult = changePassCtx.updatePassword(userInfo, newPass);
                    if (passResult.getStatus() == Response.OK) {
                        req.setAttribute("infomsg", "Successfully updated the profile info!");
                    } else {
                        req.setAttribute("errmsg", "Failed to update the profile info!");
                    }
                }

            } else {
                req.setAttribute("infomsg", "Successfully updated the profile info!");
            }
        } else {
            req.setAttribute("errmsg", "Failed to update the profile info!");
        }

        // Register page redirect
        GenderDBContext genderCtx = new GenderDBContext();
        List<Gender> genderList = genderCtx.all().getReturnObject();
        // Assumes the retrieval goes successfully
        req.setAttribute("genderList", genderList);
        req.getRequestDispatcher("/views/customer/user-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Register page redirect
        GenderDBContext genderCtx = new GenderDBContext();
        List<Gender> genderList = genderCtx.all().getReturnObject();
        // Assumes the retrieval goes successfully
        req.setAttribute("genderList", genderList);
        req.getRequestDispatcher("/views/customer/user-profile.jsp").forward(req, resp);
    }

}
