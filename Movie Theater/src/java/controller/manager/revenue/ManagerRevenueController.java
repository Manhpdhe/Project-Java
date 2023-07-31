/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.revenue;

import dal.dao.BranchDBContext;
import dal.dao.TicketDBContext;
import dal.dao.TicketPriceDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.Branch;
import util.TicketSaleTrend;

/**
 *
 * @author CaoThuLuDau
 */
public class ManagerRevenueController extends HttpServlet{
    
    private final BranchDBContext branchCtx = new BranchDBContext();
    private final TicketDBContext ticketCtx = new TicketDBContext();
    private final TicketPriceDBContext revenueCtx = new TicketPriceDBContext();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String branchID = req.getParameter("cinema-id");
        if (branchID != null) {
            // Get all branch for filter 
            List<Branch> branchList = branchCtx.all().getReturnObject();
            req.setAttribute("branchList", branchList);

            //Get BranchTicketSale of the latest 7 day periods
            Integer currentTTS = ticketCtx.getLatestBranchTicketSale(branchID, "day", 7).getReturnObject();
            req.setAttribute("totalTicketSale", currentTTS);

            //Compare it to the TotalTicketSale of the prevous 7 days periods to get the trend
            Integer previousTTS = ticketCtx.getLatestBranchTicketSale(branchID, "day", 7 , 7).getReturnObject();
            String trend = TicketSaleTrend.ticketSaleTrend(currentTTS, previousTTS);
            req.setAttribute("trend", trend);

            //Get TotalRevenue for each month
            Map<String, Integer> monthRevenues = revenueCtx.getBranchRevenueYearMonth(branchID).getReturnObject();
            req.setAttribute("monthRevenues", monthRevenues);

            req.getRequestDispatcher("/views/manager/monitor/Ticket Sale And Revenue/MonitorTicketSaleAndRevenue.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/headmanage/revenue");
    }
    
            
}
