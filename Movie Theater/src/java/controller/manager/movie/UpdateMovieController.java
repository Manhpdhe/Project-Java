/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.movie;

import dal.dao.LanguageDBContext;
import dal.dao.MovieDBContext;
import dal.dao.RateDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import model.Language;
import model.Movie;
import model.Rate;
import util.BaseDir;
import util.RandomString;

/**
 *
 * @author HP
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UpdateMovieController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        String webRootPath = getServletContext().getRealPath("/").replace('\\', '/');
        String newFileName = RandomString.generate();
        Language language = new Language();
        language.setLanguageId(Integer.parseInt(req.getParameter("languageID")));
        
        Rate rate = new Rate();
        rate.setRatingID(Integer.parseInt(req.getParameter("ratingID")));

        Movie movieUpdate = new Movie();
        movieUpdate.setMid(Integer.parseInt(req.getParameter("mid")));
        movieUpdate.setTitle(req.getParameter("title"));
        movieUpdate.setRedob(Date.valueOf(req.getParameter("redob")));
        movieUpdate.setDuration(Integer.parseInt(req.getParameter("duration")));
        movieUpdate.setRate(rate);
        movieUpdate.setLanguage(language);
        movieUpdate.setMdesc(req.getParameter("mdesc"));
        movieUpdate.setView(Integer.parseInt(req.getParameter("view")));
        movieUpdate.setBg(req.getParameter("bg"));
        movieUpdate.setURL(req.getParameter("url"));
        movieUpdate.setPoster(req.getParameter("prevFile"));
         Part filePart = req.getPart("poster");
        String filename = filePart.getSubmittedFileName();
        if (filePart.getSize()>0) {
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        String newFile = newFileName + "." + extension;
        filePart.write((webRootPath + "upload/poster/" + newFile).replace('/', '\\'));
        movieUpdate.setPoster("poster/" + newFile);
        }

        MovieDBContext movieCtx = new MovieDBContext();
        movieCtx.update(movieUpdate);
        resp.sendRedirect(BaseDir.connect(req, "headmanage/movie"));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("mid").isEmpty()) {
            req.setAttribute("msg", "Please choose the moive you want to update here");
            req.getRequestDispatcher("/views/manager/movie/movie_list.jsp").forward(req, resp);
        } else {
            MovieDBContext movieCtx = new MovieDBContext();
            List<Movie> movie = movieCtx.all().getReturnObject();
            req.setAttribute("movie", movie);
            int mid = Integer.parseInt(req.getParameter("mid"));

            RateDBContext rateCtx = new RateDBContext();
            List<Rate> rateList = rateCtx.all().getReturnObject();
            req.setAttribute("rateList", rateList);

            LanguageDBContext languageCtx = new LanguageDBContext();
            List<Language> languageList = languageCtx.all().getReturnObject();
            req.setAttribute("languageList", languageList);
            
            Movie movieUpdate = null;
            for (Movie r : movie) {
                if (r.getMid() == mid) {
                    movieUpdate = r;
                }
            }
            req.setAttribute("movieUpdate", movieUpdate);
            req.getRequestDispatcher("/views/manager/movie/movie_update.jsp").forward(req, resp);

        }

    }
}
