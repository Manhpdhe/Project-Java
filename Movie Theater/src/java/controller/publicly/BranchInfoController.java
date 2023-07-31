/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.publicly;

import dal.dao.BranchDBContext;
import dal.dao.ClassifyDBContext;
import dal.dao.GenreDBContext;
import dal.dao.MovieDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Branch;
import model.Classify;
import model.Genre;
import model.Movie;

/**
 *
 * @author Admin
 */
@WebServlet(
        urlPatterns="/branchinfo",
        initParams = {
            @WebInitParam(name="branch", value="-1")
        }
)
public class BranchInfoController  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //list all movie
        MovieDBContext movieCtx = new MovieDBContext();
        List<Movie> movieList = movieCtx.all().getReturnObject();
        
        //list all genre
        GenreDBContext genreCtx = new GenreDBContext();
        List<Genre> genreList = genreCtx.all().getReturnObject();
        
        //list all classify
        ClassifyDBContext classifyCtx = new ClassifyDBContext();
        List<Classify> classifyList = classifyCtx.all().getReturnObject();
        
        //list all Cinema
        BranchDBContext branchCtx = new BranchDBContext();
        List<Branch> branchList = branchCtx.all().getReturnObject();
        
        req.setAttribute("classifyList", classifyList);
        req.setAttribute("genreList", genreList);
        req.setAttribute("movieList", movieList);
        req.setAttribute("branchList", branchList);
        req.getRequestDispatcher("/views/public/cinema_info.jsp").forward(req,resp);
    }
    
}
