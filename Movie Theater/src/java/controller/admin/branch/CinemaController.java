/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.branch;

import dal.dao.CinemaDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Cinema;

/**
 *
 * @author DUONG THANH LUAN
 */
public class CinemaController extends HttpServlet {

    private CinemaDBContext cinemaDBContext = new CinemaDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (null == action) {
            List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
            request.setAttribute("cinemas", cinemas);
            request.getRequestDispatcher("/views/admin/cinema/cinema_infor.jsp").forward(request, response);
        } else {
            switch (action) {
                case "delete":
                    String cinemaID = request.getParameter("cinemaId");
                    Cinema cinemaToFindDelete = new Cinema();
                    cinemaToFindDelete.setBranchID(Integer.parseInt(cinemaID));
                    Cinema cinemaToDelete = cinemaDBContext.get(cinemaToFindDelete).getReturnObject();
                    if (cinemaDBContext.delete(cinemaToDelete).getReturnObject().get("delete")) {
                        request.setAttribute("msg", "Delete cinema successfully");
                    } else {
                        request.setAttribute("msg", "Delete cinema fail");
                    }
                    List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
                    request.setAttribute("cinemas", cinemas);
                    request.getRequestDispatcher("/views/admin/cinema/cinema_infor.jsp").forward(request, response);
                    break;
                case "update":
                    String id = request.getParameter("cinemaId");
                    Cinema cinemaToFindUpdate = new Cinema();
                    cinemaToFindUpdate.setBranchID(Integer.parseInt(id));
                    Cinema cinemaToUpdate = cinemaDBContext.get(cinemaToFindUpdate).getReturnObject();
                    request.setAttribute("cinema", cinemaToUpdate);
                    request.getRequestDispatcher("/views/admin/cinema/cinema_update.jsp").forward(request, response);
                    break;
                case "create":
                    request.getRequestDispatcher("/views/admin/cinema/cinema_create.jsp").forward(request, response);
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("update")) {
            String branchId = request.getParameter("branchId");
            String brachName = request.getParameter("branchName");
            String location = request.getParameter("location");
            Cinema cinema = new Cinema(Integer.parseInt(branchId), location, brachName);
            if (cinemaDBContext.update(cinema).getReturnObject().get("update")) {
                request.setAttribute("msg", "Update cinema successfully");
            } else {
                request.setAttribute("msg", "Update cinema fail");
            }
            request.getRequestDispatcher("/views/admin/cinema/cinema_update.jsp").forward(request, response);

        } else if (action.equals("create")) {
            String brachName = request.getParameter("branchName");
            String location = request.getParameter("location");
            Cinema cinema = new Cinema(location, brachName);
            if (cinemaDBContext.insert(cinema).getReturnObject().get("insert")) {
                request.setAttribute("msg", "Create cinema successfully");
            } else {
                request.setAttribute("msg", "Create cinema fail");
            }
            request.getRequestDispatcher("/views/admin/cinema/cinema_create.jsp").forward(request, response);
        }
    }

}
