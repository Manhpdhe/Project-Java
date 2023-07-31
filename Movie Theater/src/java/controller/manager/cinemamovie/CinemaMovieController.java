/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.cinemamovie;

import dal.dao.BranchMovieDBContext;
import dal.dao.MovieDBContext;
import dal.dao.ShowDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.Movie;
import model.Branch;
import model.BranchMovie;

/**
 *
 * @author CaoThuLuDau
 */
public class CinemaMovieController extends HttpServlet {

    private MovieDBContext moviesDBContext = new MovieDBContext();
    private BranchMovieDBContext branchMovieDBContext = new BranchMovieDBContext();
    private ShowDBContext showsDBContext = new ShowDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (null == action) {
            //Get BranchID
            int branchID = Integer.parseInt(request.getParameter("branchID"));
            request.setAttribute("branchID", branchID);
            
            //Get movies of the cinema
            List<Movie> moviesOfCinema = branchMovieDBContext.getMoviesByBranchID(branchID).getReturnObject();
            request.setAttribute("moviesOfCinema", moviesOfCinema);

            //Get all movies that not of the cinema
            List<Movie> movies = moviesDBContext.all().getReturnObject();
            Iterator<Movie> iterator = movies.iterator();
            while (iterator.hasNext()) {
                Movie m = iterator.next();
                for (Movie moc : moviesOfCinema) {
                    if (Objects.equals(m.getMid(), moc.getMid())) {
                        iterator.remove();
                        break;
                    }
                }
            }
            System.out.println("BRUH");
            request.setAttribute("movies", movies);

            //Get number of shows of movies of the cinema
            Map<Integer, Integer> showCountOfMovies = new HashMap<>();
            for (Movie m : moviesOfCinema) {
                int countResult = showsDBContext.countShowsByMovieInBranch(branchID, m.getMid()).getReturnObject();
                showCountOfMovies.put(m.getMid(), countResult);
            }
            request.setAttribute("showCountOfMovies", showCountOfMovies);

            request.getRequestDispatcher("/views/manager/movie/cinema_movie.jsp").forward(request, response);
        } else {
            switch (action) {
                case "delete":
//                    String cinemaID = request.getParameter("cinema-id");
//                    Cinema cinemaToFindDelete = new Cinema();
//                    cinemaToFindDelete.setBranchID(Integer.parseInt(cinemaID));
//                    Cinema cinemaToDelete = cinemaDBContext.get(cinemaToFindDelete).getReturnObject();
//                    if (cinemaDBContext.delete(cinemaToDelete).getReturnObject().get("delete")) {
//                        request.setAttribute("msg", "Delete cinema successfully");
//                    } else {
//                        request.setAttribute("msg", "Delete cinema fail");
//                    }
//                    List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
//                    request.setAttribute("cinemas", cinemas);
//                    request.getRequestDispatcher("/views/manager/cinema/cinema_infor.jsp").forward(request, response);
                    break;
                case "update":
//                    List<Cinema> cinemasToUpdate = cinemaDBContext.all().getReturnObject();
//                    request.setAttribute("cinemas", cinemasToUpdate);
//                    request.getRequestDispatcher("/views/manager/cinema/cinema_update.jsp").forward(request, response);
                    break;
                case "create":
//                    request.getRequestDispatcher("/views/manager/cinema/cinema_create.jsp").forward(request, response);
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
        if (action.equals("insert")) {
            int branchID = Integer.parseInt(request.getParameter("branch-id"));
            int movieID = Integer.parseInt(request.getParameter("selectedMovie"));
            
            Branch b = new Branch();
            b.setBranchID(branchID);
            
            Movie m = new Movie();
            m.setMid(movieID);
            
            BranchMovie branchMovie = new BranchMovie(b, m);
            if (branchMovieDBContext.insert(branchMovie).getReturnObject().get("inserted")) {
                request.setAttribute("msg", "Update cinema successfully");
            } else {
                request.setAttribute("msg", "Update cinema fail");
            }
        response.sendRedirect("/headmanage/cinemamovies?branchID=" + branchID);

        } else if (action.equals("create")) {
//            String brachName = request.getParameter("cinema-name");
//            String location = request.getParameter("cinema-location");
//            Cinema cinema = new Cinema(location, brachName);
//            if (cinemaDBContext.insert(cinema).getReturnObject().get("inserted")) {
//                request.setAttribute("msg", "Create cinema successfully");
//            } else {
//                request.setAttribute("msg", "Create cinema fail");
//            }
//            List<Cinema> cinemas = cinemaDBContext.all().getReturnObject();
//            request.setAttribute("cinemas", cinemas);
//            request.getRequestDispatcher("/views/manager/cinema/cinema_infor.jsp").forward(request, response);
        }
    }
}
