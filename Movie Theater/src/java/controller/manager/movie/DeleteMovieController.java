/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.movie;

import dal.dao.MovieDBContext;
import dal.dao.ShowDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Movie;
import model.Response;
import util.BaseDir;

/**
 *
 * @author HP
 */
public class DeleteMovieController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operationType");
        int movieId = Integer.valueOf(req.getParameter("movie"));
        MovieDBContext movieCtx = new MovieDBContext();
        Movie movie = new Movie();
        movie.setMid(movieId);
        switch(operation) {
            case "show":
                {
                    ShowDBContext showCtx = new ShowDBContext();
                    if (showCtx.deleteShowsByMovie(movie).getStatus() == Response.OK) {
                        movieCtx.delete(movie);
                    } else {
                        
                    }
                    resp.sendRedirect(BaseDir.connect(req, "headmanage/movie"));
                    break;
                }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int mid = Integer.valueOf(req.getParameter("mid"));
            Movie movie = new Movie();
            movie.setMid(mid);
            MovieDBContext delete = new MovieDBContext();
            if (delete.delete(movie).getReturnObject().get("process")) {
                resp.sendRedirect(BaseDir.connect(req, "headmanage/movie"));
            } else {
                // Returns a confirmation screen for delete
                // Shows first
                ShowDBContext showCtx = new ShowDBContext();
                Response<Integer> numShowsRes = showCtx.countShowsByMovie(movie);
                if (numShowsRes.getStatus() == Response.OK) {
                    req.setAttribute("movie", movie);
                    req.setAttribute("opType", "show");
                    req.setAttribute("numShows", numShowsRes.getReturnObject());
                    req.getRequestDispatcher("/views/manager/movie/movie_delete.jsp").forward(req,resp);
                }
            }

        } catch (Exception e) {
            System.err.println("Fail");

        }
    }

}
