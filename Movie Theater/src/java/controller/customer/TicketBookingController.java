/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.customer;

import cfg.Config;
import dal.dao.OrderDBContext;
import dal.dao.SeatDBContext;
import dal.dao.ShowDBContext;
import dal.dao.TicketDBContext;
import dal.dao.TicketHistoryDBContext;
import dal.dao.TicketPriceDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import job.PaymentTimeoutJob;
import model.Customer;
import model.Order;
import model.Response;
import model.Seat;
import model.Show;
import model.Ticket;
import model.TicketHistory;
import model.TicketPrice;
import util.BaseDir;
import util.JobInstance;
import util.MISC;

/**
 *
 * @author Admin
 */
@WebServlet(urlPatterns = "/book")
public class TicketBookingController extends HttpServlet {

    private Seat[][] mapAsMatrixArray(int rows, int cols, List<Seat> seats) {
        Seat[][] seatArray = new Seat[rows][cols];

        for (Seat s : seats) {
            seatArray[s.getRealRowNo()][s.getRealColNo()] = s;
        }

        return seatArray;
    }

    private Ticket[][] ticketMapAsMatrixArray(int rows, int cols, List<Ticket> tickets) {
        Ticket[][] ticketArray = new Ticket[rows][cols];

        for (Ticket t : tickets) {
            ticketArray[t.getSeat().getRowNo()][t.getSeat().getRealColNo()] = t;
        }

        return ticketArray;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean orderIsValid = false;

        OrderDBContext orderCtx = new OrderDBContext();
        TicketPriceDBContext priceCtx = new TicketPriceDBContext();
        TicketHistoryDBContext historyCtx = new TicketHistoryDBContext();
        ShowDBContext showCtx = new ShowDBContext();
        Map<Integer, TicketPrice> priceMap = new HashMap<>();
        priceMap = priceCtx.allRecent().getReturnObject();
        Order order = null;
        Show sh = new Show();
        TicketHistory th = null;
        String[] seatPosList = req.getParameterValues("seatPosList");
        String timeStr = req.getParameter("timeRng");
        try {
            order = new Order();
            order.setCustomer((Customer) req.getSession().getAttribute("customer"));
            order.setOrderTime(Timestamp.valueOf(LocalDateTime.now()));

            int showId = Integer.parseInt(req.getParameter("show"));
            Show s = new Show();
            s.setShowID(showId);
            sh = showCtx.get(s).getReturnObject();

            String[] seatIdList = req.getParameterValues("seatList");
            String[] ticketPriceList = req.getParameterValues("priceList");
            List<Ticket> tickets = new ArrayList<>();

            for (int i = 0; i < seatIdList.length; i++) {
                Ticket t = new Ticket();
                t.setShow(sh);

                Seat se = new Seat();
                se.setSeatId(Integer.parseInt(seatIdList[i]));
                t.setSeat(se);

                TicketPrice tp = new TicketPrice();
                tp.setTicketPriceId(Integer.parseInt(ticketPriceList[i]));
                t.setTicketPrice(tp);

                tickets.add(t);
            }
            Response<Map<String, Boolean>> insertRes = orderCtx.insertWithTickets(order, tickets);
            if (insertRes.getStatus() == Response.OK) {

                orderIsValid = true;
            }

        } catch (Exception ex) {

        }

        if (orderIsValid) {
            // Goes straight into vnPay
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "250000";
            long amount = Integer.parseInt(req.getParameter("amount")) * 100;
            String bankCode = "VNBANK";

            String vnp_TxnRef = Config.getRandomNumber(8);
            String vnp_IpAddr = Config.getIpAddress(req);
            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");

            if (bankCode != null && !bankCode.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bankCode);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);

            String locate = req.getParameter("language");
            if (locate != null && !locate.isEmpty()) {
                vnp_Params.put("vnp_Locale", locate);
            } else {
                vnp_Params.put("vnp_Locale", "vn");
            }
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            java.util.Date crDate = cld.getTime();
            String vnp_CreateDate = formatter.format(crDate);
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.SECOND, Config.timeout);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

            try {
                Map<String, Object> mapping = new HashMap<>();
                mapping.put("order", order);
                th = new TicketHistory();

                th.setOrderCode(vnp_TxnRef);
                th.setSeatsString(String.join(",", seatPosList));
                th.setTimeString(timeStr);
                th.setCustomer((Customer) req.getSession().getAttribute("customer"));
                th.setOrderTime(Timestamp.valueOf(LocalDateTime.now()));
                th.setMovieName(sh.getMovie().getTitle());
                th.setRoomName(sh.getTimeSlot().getRoom().getRoomName());
                th.setBranchName(sh.getTimeSlot().getRoom().getBranch().getBranchName());
                th.setPaymentStatus(2); // Pending
                th.setTotalPrice(amount / 100);
                th.setPaymentLink(paymentUrl);

                Response<Map<String, Boolean>> historyRes = historyCtx.insert(th);

                JobInstance timeoutIns = JobInstance.create(PaymentTimeoutJob.class, mapping, String.format("%s_Job", vnp_TxnRef), String.format("%s_Trigger", vnp_TxnRef), Config.timeout);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(paymentUrl);
            resp.sendRedirect(paymentUrl);
        } else {
            Map<String, String[]> params = new HashMap<>();
            params.put("show", new String[]{req.getParameter("show")});
            params.put("err", new String[]{"1"});
            resp.sendRedirect(BaseDir.connect(req, "book", params));
        }

        //
        /*
        req.setAttribute("orderType", 250000);
        req.setAttribute("amount", req.getParameter("amount"));
        req.setAttribute("bankCode", "VNBANK");
        req.setAttribute("language", "vn");
         */
        //req.getRequestDispatcher("/views/customer/payment_confirm.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String showId = req.getParameter("show");
        String err = req.getParameter("err");
        Show s = new Show();
        try {
            s.setShowID(Integer.parseInt(showId));
            ShowDBContext showCtx = new ShowDBContext();
            TicketPriceDBContext priceCtx = new TicketPriceDBContext();
            Response<Show> showRes = showCtx.get(s);
            TicketDBContext ticketCtx = new TicketDBContext();

            System.out.println(req.getRequestURL());

            if (showRes.getStatus() == Response.OK) {
                Show obj = showRes.getReturnObject();
                req.setAttribute("showInfo", obj);
                SeatDBContext seatCtx = new SeatDBContext();
                Response<List<Seat>> seatRes = seatCtx.getByRoom(obj.getTimeSlot().getRoom());
                Calendar cal = Calendar.getInstance();
                cal.setTime(obj.getTimeSlot().getStartTime());
                cal.add(Calendar.MINUTE, obj.getMovie().getDuration());
                cal.add(Calendar.MILLISECOND, (int) obj.getStartDelay());

                Timestamp endTime = new Timestamp(cal.getTimeInMillis());
                req.setAttribute("timeStart", obj.getTimeSlot().getStartTime());
                req.setAttribute("timeEnd", endTime);
                if (seatRes.getStatus() == Response.OK) {
                    req.setAttribute("rowLetters", MISC.alphabeticList(obj.getTimeSlot().getRoom().getNumRows()));

                    Map<Integer, TicketPrice> priceMap = new HashMap<>();
                    priceMap = priceCtx.allRecent().getReturnObject();

                    req.setAttribute("ticketPrices", priceMap);
                    req.setAttribute("seatMatrix", mapAsMatrixArray(obj.getTimeSlot().getRoom().getNumRows(), obj.getTimeSlot().getRoom().getNumCols(), seatRes.getReturnObject()));
                    req.setAttribute("ticketMatrix", ticketMapAsMatrixArray(obj.getTimeSlot().getRoom().getNumRows(), obj.getTimeSlot().getRoom().getNumCols(), ticketCtx.byShow(obj).getReturnObject()));

                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            if (err != null) {
                req.setAttribute("errmsg", "Có lỗi xảy ra trong quá trình đặt vé!");
            }

            req.getRequestDispatcher("/views/customer/seat-booking.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
            //resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

}
