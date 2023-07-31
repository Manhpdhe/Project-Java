package controller.publicly;

import dal.dao.BranchDBContext;
import dal.dao.MovieDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Branch;
import model.Movie;

@WebServlet(urlPatterns = "/search")
public class SearchMovieController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String txtSearch = req.getParameter("txt");
        int branchId = Integer.parseInt(req.getParameter("filter"));  
        System.err.println("BranchID: " + branchId);
        // Perform movie search
        MovieDBContext movieCtx = new MovieDBContext();
        List<Movie> movieList;
        if (branchId != -1) {
            movieList = movieCtx.getMovieByBranchId(branchId);
        } else {
            movieList = movieCtx.searchByMovie(txtSearch);
        }
        
        List<Movie> movieL = movieCtx.all().getReturnObject();
        BranchDBContext branchCtx = new BranchDBContext();
        List<Branch> branchList = branchCtx.all().getReturnObject();

        req.setAttribute("branchList", branchList);
        req.setAttribute("branchId", branchId);
        req.setAttribute("movieL", movieL);
        req.setAttribute("movieList", movieList);
        req.setAttribute("txtSearch", txtSearch);
        req.getRequestDispatcher("/views/public/movies_list.jsp").forward(req, resp);

    }
}
