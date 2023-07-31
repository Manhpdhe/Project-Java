/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.Customer;

/**
 *
 * @author Tien Dung purpose: Filter controller for Customer Authorization
 */
public class CustomerAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest srq, ServletResponse srsp, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) srq;
        HttpServletResponse resp = (HttpServletResponse) srsp;
        // First, get via session
        Customer user = (Customer) req.getSession().getAttribute("customer");

        // If not, get via 
        if (user != null) {
            if (user.isVerified()) {
                fc.doFilter(req, resp);
            } else {
                // Substringfrom index 1 to get rid of initial slash
                String intendedDes = req.getServletPath().substring(1);
                // Login to continue message
                req.setAttribute("warnmsg", "Bạn phải đăng nhập để tiếp tục sử dụng chức năng!");
                req.setAttribute("destination", intendedDes);
                req.setAttribute("obtainedParams", req.getParameterMap());
                // Goes to the login page
                req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
            }
        } else {
            // Substringfrom index 1 to get rid of initial slash
            String intendedDes = req.getServletPath().substring(1);
            // Login to continue message
            req.setAttribute("warnmsg", "Bạn phải đăng nhập để tiếp tục sử dụng chức năng!");
            req.setAttribute("destination", intendedDes);
            req.setAttribute("obtainedParams", req.getParameterMap());
            // Goes to the login page
            req.getRequestDispatcher("/views/account/login.jsp").forward(req, resp);
        }
    }

}
