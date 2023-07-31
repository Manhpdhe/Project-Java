/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.movie;

import dal.dao.MovieDBContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Movie;

/**
 *
 * @author HP
 */
public class ListMovieController extends HttpServlet {

    private List<Movie> classifySeat(List<Movie> movies, Integer pageIndex) {
        List<Movie> subSeat;
        Integer currentIndex = pageIndex * 4;
        Integer endIndex = currentIndex + 4;
        if (endIndex < movies.size()) {
            subSeat = movies.subList(currentIndex, currentIndex + 4);
        } else {
            subSeat = movies.subList(currentIndex, movies.size());
        }

        return subSeat;
    }

    private void separatePage(List<Movie> seats, String currentPage, HttpServletRequest request) {
        // Get length of current page
        Double lengthOfPage = Math.ceil((float) seats.size() / 4);
        // Check current page is null or equals to 0, add 10 records from database
        if (currentPage == null || currentPage.equals("0")) {
            List<Movie> subSeats = classifySeat(seats, 0);
            request.setAttribute("m", subSeats);
        } else {
            // Get 10 next records depend on choosing page
            // Condition: current page is less than length of page. The system keep going on up
            if (Double.parseDouble(currentPage) < lengthOfPage) {
                List<Movie> subSeats = classifySeat(seats, Integer.parseInt(currentPage));
                request.setAttribute("m", subSeats);
            } // Condition: current page is greater than length of page then return last page value
            else if (Double.parseDouble(currentPage) >= lengthOfPage) {
                List<Movie> subSeats = classifySeat(seats, (int) (lengthOfPage - 1));
                request.setAttribute("m", subSeats);
            } // Condition: Current page is less than 0. Then we return the index 0 of page
            else {
                List<Movie> subSeats = classifySeat(seats, 0);
                request.setAttribute("m", subSeats);
            }
        }
        // Send current page to act previous and next function
        request.setAttribute("currentPage", currentPage);
        // Send length of pages to seat.jsp to separate page
        request.setAttribute("lengthPage", lengthOfPage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MovieDBContext m = new MovieDBContext();
        List<Movie> list = m.all().getReturnObject();
        String currentPage = req.getParameter("page");
        separatePage(list, currentPage, req);
        //req.setAttribute("m", list);
        req.getRequestDispatcher("/views/manager/movie/movie_list.jsp").forward(req, resp);

    }

}
