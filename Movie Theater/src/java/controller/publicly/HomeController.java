/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.publicly;

import dal.dao.ClassifyDBContext;
import dal.dao.GenreDBContext;
import dal.dao.MovieDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Classify;
import model.Genre;
import model.Movie;

/**
 *
 * @author Admin
 */
@WebServlet(urlPatterns="/home")
public class HomeController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/public/homepage.jsp").forward(req,resp);
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
        
        req.setAttribute("classifyList", classifyList);
        req.setAttribute("genreList", genreList);
        req.setAttribute("movieList", movieList);
        req.getRequestDispatcher("/views/public/homepage.jsp").forward(req,resp);
    }
    
}
