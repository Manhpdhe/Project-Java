/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.account;

import dal.dao.CustomerDBContext;
import dal.dao.VerificationDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import mailing.MailObject;
import mailing.Mailer;
import model.Customer;
import model.Response;
import model.Verification;
import util.BaseDir;
import util.RandomString;

/**
 *
 * @author Admin
 */
@WebServlet(urlPatterns = "/verify")
public class VerifyController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("vertype");
        int typeInt = 0;
        try {
            typeInt = Integer.parseInt(type);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            System.out.println(e.getMessage());
            return;
        }

        String[] otps = req.getParameterValues("otp");
        String fullOtp = String.join("", otps);
        System.out.println(fullOtp);
        VerificationDBContext verCtx = new VerificationDBContext();
        Customer c = new Customer();
        if (req.getSession().getAttribute("customer") != null) {
            c = (Customer) req.getSession().getAttribute("customer");
        } else {
            c = (Customer) req.getSession().getAttribute("tempCustomer");
        }
        System.out.println(c);
        Response<Verification> verifyRes = verCtx.getRecentCodeByAccount(c);
        if (verifyRes.getStatus() == Response.OK) {
            System.out.println(fullOtp + " - " + verifyRes.getReturnObject());
            if (verifyRes.getReturnObject().getCode().equals(fullOtp)) {
                Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                if (cld.getTimeInMillis() - verifyRes.getReturnObject().getCreateTime().getTime() > 60*10*1000) { // > 10 min
                    req.setAttribute("errmsg", "OTP has been expired! Please resent the verifcation code!");
                    req.getRequestDispatcher("/views/account/account-veri.jsp").forward(req, resp);
                    return;
                }
                switch (typeInt) {
                    case 1: // Acc creation ver
                    {
                        CustomerDBContext cusCtx = new CustomerDBContext();
                        cusCtx.confirmVerification(c);
                        c.setVerified(true);
                        req.getSession().setAttribute("customer", c);
                        resp.sendRedirect(BaseDir.connect(req, "home"));
                        break;
                    }
                    case 2: // Forgot pass ver
                    {
                        int cusId = c.getCustomerId();
                        resp.sendRedirect(BaseDir.connect(req, String.format("recover?customer=%s&otp=%s",cusId,fullOtp)));
                        return;
                    }
                }
                
            } else {
                req.setAttribute("vertype", type);
                req.setAttribute("errmsg", "OTP is incorrect!");
                req.getRequestDispatcher("/views/account/account-veri.jsp").forward(req, resp);

            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("vertype");
        int typeInt = 0;
        try {
            typeInt = Integer.parseInt(type);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        Customer c = new Customer();
        if (typeInt == 1) {
            c = (Customer) req.getSession().getAttribute("customer");
        }
        else {
            c = (Customer) req.getSession().getAttribute("tempCustomer");
        }
        
        VerificationDBContext verCtx = new VerificationDBContext();
        String resend = req.getParameter("resend");

        Map<String, String> params = new HashMap<>();
        String otp = RandomString.generateOTP(7);
        params.put("username", c.getAccountName());
        params.put("otp_code", otp);

        if (resend != null) {
            if (resend.equals("1")) {
                req.setAttribute("infomsg", "OTP has been sent to your email!");
            }
        }

        Verification ver = new Verification();
        ver.setCustomer(c);
        ver.setCode(otp);
        ver.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));

        verCtx.insert(ver);

        System.out.println(c.getEmail());
        MailObject mo = null;
        if (typeInt == 1) {
            mo = MailObject.fromHTMLTemplate("Confirm Groovy Cineplex Account", "verifymail_temp.html", params);
        }
        else if (typeInt == 2) {
            mo = MailObject.fromHTMLTemplate("Reset Groovy Cineplex Account Password", "forgotpass_temp.html", params);
        }
        Mailer.send(c.getEmail(), mo);

        req.setAttribute("vertype", type);
        req.getRequestDispatcher("/views/account/account-veri.jsp").forward(req, resp);
    }

}
