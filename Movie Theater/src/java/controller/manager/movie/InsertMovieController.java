/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.movie;
import cfg.MyConfig;
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
import util.FileUploadManager;

/**
 *
 * @author HP
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class InsertMovieController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        
        
        
        String title = req.getParameter("title");
        Date redob = Date.valueOf(req.getParameter("redob"));
        int duration = Integer.valueOf(req.getParameter("duration"));
        int ratingid = Integer.valueOf(req.getParameter("ratingid"));
        String mdesc = req.getParameter("mdesc");
        String poster = req.getParameter("poster");
        int view = Integer.valueOf(req.getParameter("view"));
        String bg = req.getParameter("bg");
        String url =req.getParameter("url");
        int languageid = Integer.valueOf(req.getParameter("languageid"));
        
        Language language = new Language();
        language.setLanguageId(languageid);
        
        Rate rate = new Rate();
        rate.setRatingID(ratingid);

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setRedob(redob);
        movie.setDuration(duration);
        movie.setRate(rate);
        movie.setMdesc(mdesc);
        movie.setView(view);
        movie.setLanguage(language);
        movie.setURL(url);
        movie.setBg(bg);

        System.out.println(movie);

        FileUploadManager fileUploader = new FileUploadManager(MyConfig.retrieve());
        
        Part filePart = req.getPart("poster");
        String filename = fileUploader.saveFile("poster", filePart);
        
        movie.setPoster("poster/" + filename);

        MovieDBContext movieCtx = new MovieDBContext();
        movieCtx.insert(movie);
        resp.sendRedirect(BaseDir.connect(req, "headmanage/movie"));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RateDBContext lrateCtx = new RateDBContext();
        MovieDBContext listCtx = new MovieDBContext();
        LanguageDBContext llanguage = new LanguageDBContext();
        
        List<Movie> movieCtx = listCtx.all().getReturnObject();
        List<Rate> rateCtx = lrateCtx.all().getReturnObject();
        List<Language> languageCtx = llanguage.all().getReturnObject();
        
        req.setAttribute("languageCtx", languageCtx);
        req.setAttribute("movieCtx", movieCtx);
        req.setAttribute("rateCtx", rateCtx);
        req.getRequestDispatcher("/views/manager/movie/movie_create.jsp").forward(req, resp);
    }

}
