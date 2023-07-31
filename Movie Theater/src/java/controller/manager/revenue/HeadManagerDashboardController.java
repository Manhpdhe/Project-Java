/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.revenue;

import dal.dao.BranchDBContext;
import dal.dao.CustomerDBContext;
import dal.dao.OrderDBContext;
import dal.dao.RoomDBContext;
import dal.dao.TicketDBContext;
import dal.dao.TicketPriceDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author CaoThuLuDau
 */
public class HeadManagerDashboardController extends HttpServlet {

    private final BranchDBContext branchCtx = new BranchDBContext();
    private final RoomDBContext roomCtx = new RoomDBContext();
    private final CustomerDBContext customerCtx = new CustomerDBContext();
    private final OrderDBContext orderCtx = new OrderDBContext();
    private final TicketDBContext ticketCtx = new TicketDBContext();
    private final TicketPriceDBContext revenueCtx = new TicketPriceDBContext();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Get Total number of branches
        int branchNumber = branchCtx.getNumberOfBranches().getReturnObject();
        req.setAttribute("branchNumber", branchNumber);

        //Get Total number of rooms
        int roomNumber = roomCtx.getNumberOfRooms().getReturnObject();
        req.setAttribute("roomNumber", roomNumber);

        //Get Total number of customers
        int cusNumber = customerCtx.getNumberOfCustomers().getReturnObject();
        req.setAttribute("cusNumber", cusNumber);

        //Get NumberOfBuyers past 7 days
        Map<String, Integer> buyers = orderCtx.getTotalBuyer7Days().getReturnObject();
        req.setAttribute("buyers", buyers);

        //Get TotalRevenue past 7 days
        Map<String, Integer> revenues = revenueCtx.getTotalRevenue7Days().getReturnObject();
        req.setAttribute("revenues", revenues);

        //Get top 5 highest watched movies this week
        Map<String, Integer> top5Movies = ticketCtx.getTop5HighestWatchedMovieThisWeek().getReturnObject();
        req.setAttribute("top5Movies", top5Movies);

        req.getRequestDispatcher("/views/manager/monitor/Admin Dashboard/AdminDashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                //Get Total number of branches
        int branchNumber = branchCtx.getNumberOfBranches().getReturnObject();
        req.setAttribute("branchNumber", branchNumber);

        //Get Total number of rooms
        int roomNumber = roomCtx.getNumberOfRooms().getReturnObject();
        req.setAttribute("roomNumber", roomNumber);

        //Get Total number of customers
        int cusNumber = customerCtx.getNumberOfCustomers().getReturnObject();
        req.setAttribute("cusNumber", cusNumber);

        //Get NumberOfBuyers past 7 days
        Map<String, Integer> buyers = orderCtx.getTotalBuyer7Days().getReturnObject();
        req.setAttribute("buyers", buyers);
        
//        //Get TicketSales past 7 days
//        Map<String, Integer> ticketSales = ticketCtx.getTicketSale7Days().getReturnObject();
//        req.setAttribute("ticketSales", ticketSales);

        //Get TotalRevenue past 7 days
        Map<String, Integer> revenues = revenueCtx.getTotalRevenue7Days().getReturnObject();
        req.setAttribute("revenues", revenues);

        //Get top 5 highest watched movies this week
        Map<String, Integer> top5Movies = ticketCtx.getTop5HighestWatchedMovieThisWeek().getReturnObject();
        req.setAttribute("top5Movies", top5Movies);
        
        
        String action = req.getParameter("action");
        String viewType = req.getParameter("viewType");
        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "changeBuyersViewType":
                    // Handle "changeBuyersViewType" action
                    if (viewType.equals("7D")) { //7 days
                        buyers = orderCtx.getTotalBuyer7Days().getReturnObject();
                        req.setAttribute("buyers", buyers);
                    } else if (viewType.equals("YM")) { //year by month
                        buyers = orderCtx.getTotalBuyerYearMonth().getReturnObject();
                        req.setAttribute("buyers", buyers);
                    } else if (viewType.equals("YQ")) { //year by 3 month
                        buyers = orderCtx.getTotalBuyerYearQuarter().getReturnObject();
                        req.setAttribute("buyers", buyers);
                    } else if (viewType.equals("7DV7D")) { //7 days vs 7 previous days
                        buyers = orderCtx.getTotalBuyer7Days().getReturnObject();
                        req.setAttribute("buyers", buyers);
                        Map<String, Integer> buyersPre = orderCtx.getTotalBuyer7DaysPrevious().getReturnObject();
                        req.setAttribute("buyersPre", buyersPre);
                    }
                    req.getRequestDispatcher("/views/manager/monitor/Admin Dashboard/AdminDashboard.jsp").forward(req, resp);
                    break;
                case "changeRevenuesViewType":
                    // Handle "changeRevenuesViewType" action
                    if (viewType.equals("7D")) { //7 days
                        revenues = revenueCtx.getTotalRevenue7Days().getReturnObject();
                        req.setAttribute("revenues", revenues);
                    } else if (viewType.equals("YM")) { //year by month
                        revenues = revenueCtx.getTotalRevenueYearMonth().getReturnObject();
                        req.setAttribute("revenues", revenues);
                    } else if (viewType.equals("YQ")) { //year by quarter
                        revenues = revenueCtx.getTotalRevenueYearQuarter().getReturnObject();
                        req.setAttribute("revenues", revenues);
                    } else if (viewType.equals("7DV7D")) { //7 days vs 7 previous days
                        revenues = revenueCtx.getTotalRevenue7Days().getReturnObject();
                        req.setAttribute("revenues", revenues);
                        Map<String, Integer> revenuesPre = revenueCtx.getTotalRevenue7DaysPrevious().getReturnObject();
                        req.setAttribute("revenuesPre", revenuesPre);
                    }
                    req.getRequestDispatcher("/views/manager/monitor/Admin Dashboard/AdminDashboard.jsp").forward(req, resp);
                    break;
                case "changeMoviesViewType":
                    // Handle "changeMoviesViewType" action
                    if (viewType.equals("W")) { //this week
                        top5Movies = ticketCtx.getTop5HighestWatchedMovieThisWeek().getReturnObject();
                        req.setAttribute("top5Movies", top5Movies);
                    } else if (viewType.equals("M")) { //this month
                        top5Movies = ticketCtx.getTop5HighestWatchedMovieThisMonth().getReturnObject();
                        req.setAttribute("top5Movies", top5Movies);
                    } else if (viewType.equals("Q")) { //this Quarter
                        top5Movies = ticketCtx.getTop5HighestWatchedMovieThisQuarter().getReturnObject();
                        req.setAttribute("top5Movies", top5Movies);
                    } else if (viewType.equals("Y")) { //this year
                        top5Movies = ticketCtx.getTop5HighestWatchedMovieThisYear().getReturnObject();
                        req.setAttribute("top5Movies", top5Movies);
                    }
                    req.getRequestDispatcher("/views/manager/monitor/Admin Dashboard/AdminDashboard.jsp").forward(req, resp);
                    break;
                default:
                    this.doGet(req, resp);
                    break;
            }
        } else {
            this.doGet(req, resp);
        }
    }

}
