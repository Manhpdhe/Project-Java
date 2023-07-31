package controller.publicly;

import DTO.MappingDateAndSchedule;
import dal.dao.BranchDBContext;
import dal.dao.CastDBContext;
import dal.dao.ClassifyDBContext;
import dal.dao.DirectDBContext;
import dal.dao.MovieDBContext;
import dal.dao.ShowDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Branch;
import model.Cast;
import model.Classify;
import model.Direct;
import model.Movie;
import model.Response;
import model.Show;

@WebServlet(
        urlPatterns = "/detailsmovie",
        initParams = {
            @WebInitParam(name = "movie", value = "-1")
        }
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class MovieDetailController extends HttpServlet {

    private static final Pattern URL_PATTERN = Pattern.compile("^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube(-nocookie)?\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$");
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    private String convertUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {

        }
        matcher.appendTail(sb);

        String convertedUrl = sb.toString();
        if (convertedUrl.contains("youtu.be")) {
            convertedUrl = convertedUrl.replace("youtu.be", "youtube.com/embed/");
        } else if (convertedUrl.contains("watch?v=")) {
            convertedUrl = convertedUrl.replace("watch?v=", "embed/");
        }

        return convertedUrl;
    }

    private static Date findNextDay(Date date) {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }

    private static List<MappingDateAndSchedule> mappingDate(int mid, int branchId) {
        List<MappingDateAndSchedule> mappingShows = new ArrayList<>();
        // Khai báo scheduleDBContext
        ShowDBContext showDBContext = new ShowDBContext();
        // Khai báo LocalDate
        LocalDate currentDate = LocalDate.now();
        // Tìm thời gian hiện tại của rạp chiếu
        Date now = java.sql.Date.valueOf(currentDate);
        // Tìm thời gian sau đó là 7 ngày
        Date sevenDays = java.sql.Date.valueOf(currentDate.plusDays(6));    
        // Gán cho 1 giá trị tạm thời để thực hiện việc cộng trong vòng lặp
        Date temp = now;
        // Tìm kiếm show có chi nhánh đã được khai báo và phim được khai báo với thời gian lớn hơn hoặc bàng hiện tại
        List<Show> findedShows = showDBContext.findShowByMovieIdAndBranchId(mid, branchId);
        // Thực hiện vòng lặp
        while (temp.before(sevenDays) || temp.equals(sevenDays)) {
            // Thực hiện mapping date
            List<Show> show = new ArrayList<>();
            for (Show findedShow : findedShows) {
                if (temp.toString().contains(new Date(findedShow.getTimeSlot().getStartTime().getTime()).toString())) {
                    show.add(findedShow);
                }
            }
            MappingDateAndSchedule map = new MappingDateAndSchedule(temp, show);
            mappingShows.add(map);
            temp = findNextDay(temp);

        }
        return mappingShows;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/public/movie_detail.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String movieId = req.getParameter("mid");
        String branchId = req.getParameter("branchId");
        try {
            if (branchId == null) {
                branchId = "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int movieIdInt = Integer.parseInt(movieId);

        Movie m = new Movie();
        m.setMid(movieIdInt);
        

        ShowDBContext showCtx = new ShowDBContext();
        List<Show> showRes = showCtx.getShowbyMovie(m).getReturnObject();
        BranchDBContext branchDBContext = new BranchDBContext();
        List<Branch> branchs = branchDBContext.all().getReturnObject();
        MovieDBContext movieCtx = new MovieDBContext();
        Response<Movie> movRes = movieCtx.get(m);

        DirectDBContext directCtx = new DirectDBContext();
        List<Direct> directRes = directCtx.getByMovieID(m).getReturnObject();

        ClassifyDBContext classifyCtx = new ClassifyDBContext();
        Response<List<Classify>> classifyRes = classifyCtx.getGenreByMovieID(m);

        CastDBContext castCtx = new CastDBContext();
        Response<List<Cast>> castRes = castCtx.getByMovieID(m);

        
        BranchDBContext branchCtx = new BranchDBContext();
        Response<List<Branch>> branchRes = branchCtx.all();

        if (movRes.getStatus() == Response.OK) {
            Movie movie = movRes.getReturnObject();
            String convertedUrl = convertUrl(movie.getURL());
            movie.setURL(convertedUrl);
            List<MappingDateAndSchedule> date = new ArrayList<>();
            try {
                 date = mappingDate(movieIdInt, Integer.parseInt(branchId));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            req.setAttribute("date", date);
            req.setAttribute("movie", movie);
            req.setAttribute("mid", movieIdInt);
            req.setAttribute("branchId", branchId);
            req.setAttribute("direct", directRes);
            req.setAttribute("branchs", branchs);
            req.setAttribute("showList", showRes);
            req.setAttribute("castList", castRes.getReturnObject());
            req.setAttribute("classifyList", classifyRes.getReturnObject());
            req.setAttribute("branchList", branchRes);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        req.getRequestDispatcher("/views/public/movie_detail.jsp").forward(req, resp);
    }
}
