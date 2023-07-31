/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin.seat;

import dal.dao.SeatDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Seat;
import util.BaseDir;

/**
 *
 * @author NGUYEN THANH LUAN
 */
public class SeatController extends HttpServlet {

    private final SeatDBContext seatDBContext = new SeatDBContext();

    private List<Seat> classifySeat(List<Seat> seats, Integer pageIndex) {
        List<Seat> subSeat;
        Integer currentIndex = pageIndex * 10;
        Integer endIndex = currentIndex + 10;
        if (endIndex < seats.size()) {
            subSeat = seats.subList(currentIndex, currentIndex + 10);
        } else {
            subSeat = seats.subList(currentIndex, seats.size());
        }

        return subSeat;
    }

    private void separatePage(List<Seat> seats, String currentPage, HttpServletRequest request) {
        // Get length of current page
        Double lengthOfPage = Math.ceil((float) seats.size() / 10);
        // Check current page is null or equals to 0, add 10 records from database
        if (currentPage == null || currentPage.equals("0")) {
            List<Seat> subSeats = classifySeat(seats, 0);
            request.setAttribute("allSeats", subSeats);
        } else {
            // Get 10 next records depend on choosing page
            // Condition: current page is less than length of page. The system keep going on up
            if (Double.parseDouble(currentPage) < lengthOfPage) {
                List<Seat> subSeats = classifySeat(seats, Integer.parseInt(currentPage));
                request.setAttribute("allSeats", subSeats);
            } // Condition: current page is greater than length of page then return last page value
            else if (Double.parseDouble(currentPage) >= lengthOfPage) {
                List<Seat> subSeats = classifySeat(seats, (int) (lengthOfPage - 1));
                request.setAttribute("allSeats", subSeats);
            } // Condition: Current page is less than 0. Then we return the index 0 of page
            else {
                List<Seat> subSeats = classifySeat(seats, 0);
                request.setAttribute("allSeats", subSeats);
            }
        }
        // Send current page to act previous and next function
        request.setAttribute("currentPage", currentPage);
        // Send length of pages to seat.jsp to separate page
        request.setAttribute("lengthPage", lengthOfPage);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @param session
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        List<Seat> seats = seatDBContext.all().getReturnObject();
        //List<Integer> listRoomId = seatDBContext.findAllRoomId().getReturnObject();
        String currentPage = request.getParameter("page");
        try {
            if (action == null) {
                // Check current page
                separatePage(seats, currentPage, request);
                //request.setAttribute("listRoomId", listRoomId);
                request.getRequestDispatcher("/views/admin/seat/seat_list.jsp").forward(request, response);
            } else {
                switch (action) {
                    case "create": {
                        separatePage(seats, "0", request);
                        request.getRequestDispatcher("/views/admin/seat/seat_list.jsp").forward(request, response);
                        break;
                    }
                    case "delete": {
                        String deleteSeatId = request.getParameter("seatId");
                        Integer seatId = Integer.parseInt(deleteSeatId);
                       // seatDBContext.delete(seatId);
                        separatePage(seats, "0", request);
                        request.getRequestDispatcher("/views/admin/seat/seat_list.jsp").forward(request, response);
                        break;
                    }
                    case "update": {
                        HttpSession session = request.getSession();
                        String updateSeatId = request.getParameter("seatId");
                       // Seat seat = seatDBContext.findSeatsByID(Integer.parseInt(updateSeatId)).getReturnObject();
                        separatePage(seats, "0", request);
                        //session.setAttribute("seat", seat);
                        //session.setAttribute("listRoomId", listRoomId);
                        request.getRequestDispatcher("/views/admin/seat/seat_list.jsp").forward(request, response);
                        break;
                    }
                    default:
                        separatePage(seats, "0", request);
                        //request.setAttribute("listRoomId", listRoomId);
                        request.getRequestDispatcher("/views/admin/seat/seat_list.jsp").forward(request, response);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("update")) {
                String seatId = request.getParameter("seatId");
                String rowNo = request.getParameter("rowNo");
                String seatNo = request.getParameter("seatNo");
                String roomId = request.getParameter("roomID");
               // Seat seat = new Seat(Integer.parseInt(seatId), rowNo, Integer.parseInt(seatNo), Integer.parseInt(roomId));
                /*if (seatDBContext.updateSeatById(seat).getReturnObject()) {
                    request.setAttribute("msg", "Update " + seat.getSeatID() + " successfully");
                } else {
                    request.setAttribute("msg", "Update " + seat.getSeatID() + " fail");
                }*/
                response.sendRedirect(BaseDir.connect(request, "admin/seats"));
            } else if (action.equals("create")) {
                String rowNo = request.getParameter("rowNo");
                String seatNo = request.getParameter("seatNo");
                String roomId = request.getParameter("roomId");
                //Seat seat = new Seat(rowNo, Integer.parseInt(seatNo), Integer.parseInt(roomId));
                /*if (seatDBContext.insert(seat).getReturnObject().get("processSuccessful")) {
                    request.setAttribute("msg", "Create successfully");
                } else {
                    request.setAttribute("msg", "Create fail");
                }
*/
                response.sendRedirect(BaseDir.connect(request, "admin/seats"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
