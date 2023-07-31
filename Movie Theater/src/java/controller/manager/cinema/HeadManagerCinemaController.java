/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.cinema;

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
 * @author CaoThuLuDau
 */
public class HeadManagerCinemaController extends HttpServlet{
    private CinemaDBContext cinemaDBContext = new CinemaDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (null == action) {
            List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
            request.setAttribute("cinemas", cinemas);
            request.getRequestDispatcher("/views/manager/cinema/cinema_infor.jsp").forward(request, response);
        } else {
            switch (action) {
                case "delete":
                    String cinemaID = request.getParameter("cinema-id");
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
                    request.getRequestDispatcher("/views/manager/cinema/cinema_infor.jsp").forward(request, response);
                    break;
                case "update":
//                    String id = request.getParameter("cinema-id");
//                    Cinema cinemaToFindUpdate = new Cinema();
//                    cinemaToFindUpdate.setBranchID(Integer.parseInt(id));
//                    Cinema cinemaToUpdate = cinemaDBContext.get(cinemaToFindUpdate).getReturnObject();
//                    request.setAttribute("cinema", cinemaToUpdate);
                    List<Cinema> cinemasToUpdate = cinemaDBContext.all().getReturnObject();
                    request.setAttribute("cinemas", cinemasToUpdate);
                    request.getRequestDispatcher("/views/manager/cinema/cinema_update.jsp").forward(request, response);
                    break;
                case "create":
                    request.getRequestDispatcher("/views/manager/cinema/cinema_create.jsp").forward(request, response);
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
            String branchId = request.getParameter("cinema-id");
            String brachName = request.getParameter("cinema-name");
            String location = request.getParameter("cinema-location");
            Cinema cinema = new Cinema(Integer.parseInt(branchId), location, brachName); 
            if (cinemaDBContext.update(cinema).getReturnObject().get("updated")) {
                request.setAttribute("msg", "Update cinema successfully");
            } else {
                request.setAttribute("msg", "Update cinema fail");
            }
            List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
            request.setAttribute("cinemas", cinemas);
            request.getRequestDispatcher("/views/manager/cinema/cinema_infor.jsp").forward(request, response);

        } else if (action.equals("create")) {
            String brachName = request.getParameter("cinema-name");
            String location = request.getParameter("cinema-location");
            Cinema cinema = new Cinema(location, brachName);
            if (cinemaDBContext.insert(cinema).getReturnObject().get("inserted")) {
                request.setAttribute("msg", "Create cinema successfully");
            } else {
                request.setAttribute("msg", "Create cinema fail");
            }
            List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
            request.setAttribute("cinemas", cinemas);
            request.getRequestDispatcher("/views/manager/cinema/cinema_infor.jsp").forward(request, response);
        }
    }
}
