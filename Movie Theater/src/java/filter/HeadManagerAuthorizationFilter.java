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
import model.Manager;

/**
 *
 * @author Admin
 */
public class HeadManagerAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest srq, ServletResponse srsp, FilterChain fc) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest) srq;
        HttpServletResponse resp = (HttpServletResponse) srsp;
        // First, get via session
        Manager mng = (Manager) req.getSession().getAttribute("manager");
        
        // If not, get via 
        if (mng != null) {
            if (!mng.getManagerRole().getRoleName().equals("Head Manager")) {
                fc.doFilter(req, resp);
            }
            else 
            {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        else {
            // Substringfrom index 1 to get rid of initial slash
            String intendedDes = req.getServletPath().substring(1);
            // Login to continue message
            req.setAttribute("obtainedParams", req.getParameterMap());
            req.setAttribute("warnmsg","Bạn phải đăng nhập để tiếp tục sử dụng chức năng!");
            req.setAttribute("destination",intendedDes);
            // Goes to the login page
            req.getRequestDispatcher("/views/manager/account/login.jsp").forward(req, resp);
        }
    }
    
}
