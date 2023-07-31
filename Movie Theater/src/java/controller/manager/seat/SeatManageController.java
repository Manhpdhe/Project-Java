/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.seat;

import dal.dao.RoomDBContext;
import dal.dao.SeatDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Response;
import model.Room;
import model.Seat;
import model.SeatType;
import util.BaseDir;
import util.MISC;

/**
 *
 * @author Admin
 */
@WebServlet(name = "seatControl", urlPatterns = {"/manager/seat"})
public class SeatManageController extends HttpServlet {

    private Seat[][] mapAsMatrixArray(int rows, int cols, List<Seat> seats) {
        Seat[][] seatArray = new Seat[rows][cols];
        System.out.println("NUM SEATS: " + seats.size());
        for (Seat s : seats) {
            System.out.println("SEAT REAL POS: " + s.getRealRowNo() + " " + s.getRealColNo());
            seatArray[s.getRealRowNo()][s.getRealColNo()] = s;
        }

        return seatArray;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        int numRows = Integer.parseInt(req.getParameter("numRows"));
        int numCols = Integer.parseInt(req.getParameter("numCols"));
        Room r = new Room();
        r.setRoomID(roomId);
        SeatDBContext seatCtx = new SeatDBContext();
        Response<List<Seat>> seatRes = seatCtx.getByRoom(r);
        if (seatRes.getStatus() == Response.OK) {
            Seat[][] oldSeatMatrix = mapAsMatrixArray(numRows, numCols, seatRes.getReturnObject());
            String[] seatTypes = req.getParameterValues("seatType");
            String[] seatIds = req.getParameterValues("seatId");
            
            Map<Seat,Integer> seatOperations = new HashMap<>();
            int row = 0;
            for (int i = 0; i < oldSeatMatrix.length; i++) {
                boolean hasSeat = false;
                int col = 0;
                for (int j = 0; j < oldSeatMatrix[i].length; j++) {
                    if (!(oldSeatMatrix[i][j] == null && seatTypes[i*numCols+j].equals("-1"))) {
                        
                        // DELETE OPERATION - 2
                        if (oldSeatMatrix[i][j] != null && seatTypes[i*numCols+j].equals("-1")) {
                            seatOperations.put(oldSeatMatrix[i][j], 2);
                        }
                        // INSERT OPERATION = 0
                        if (oldSeatMatrix[i][j] == null && !seatTypes[i*numCols+j].equals("-1")) {
                            hasSeat = true;
                            Seat s = new Seat();
                            s.setSeatId(Integer.parseInt(seatIds[i*numCols+j]));
                            s.setRealRowNo(i);
                            s.setRealColNo(j);
                            s.setRowNo(row);
                            s.setColNo(col);
                            
                            SeatType st = new SeatType();
                            st.setSeatTypeId(Integer.parseInt(seatTypes[i*numCols+j]));
                            s.setSeatType(st);
                            
                            s.setRoom(r);
                            seatOperations.put(s, 0);
                            col++;
                        }
                        
                        // UPDATE OPERATION = 1
                        if (oldSeatMatrix[i][j] != null && !seatTypes[i*numCols+j].equals("-1")) {
                            hasSeat = true;
                            if (oldSeatMatrix[i][j].getSeatType().getSeatTypeId() != Integer.parseInt(seatTypes[i*numCols+j])) {
                                SeatType st = new SeatType();
                                st.setSeatTypeId(Integer.parseInt(seatTypes[i*numCols+j]));
                                oldSeatMatrix[i][j].setSeatType(st);
                            }
                            oldSeatMatrix[i][j].setRowNo(row);
                            oldSeatMatrix[i][j].setColNo(col);
                            seatOperations.put(oldSeatMatrix[i][j], 1);
                            col++;
                        }
                            
                    }
                }
                if (hasSeat){
                    row++;
                }
            }
            
            SeatDBContext seatModifyCtx = new SeatDBContext();
            seatModifyCtx.modifySeatsInRoom(r, seatOperations);
            resp.sendRedirect(BaseDir.connect(req,"manager/seat?room=" + roomId));
        }
        else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomId = req.getParameter("room");
        RoomDBContext roomCtx = new RoomDBContext();
        Room r = new Room();
        r.setRoomID(Integer.parseInt(roomId));
        Response<Room> roomRes = roomCtx.get(r);
        System.out.println(roomRes.getStatus());
        if (roomRes.getStatus() == Response.OK) {
            
            Room tarRoom = roomRes.getReturnObject();
            req.setAttribute("room", tarRoom);
            SeatDBContext seatCtx = new SeatDBContext();
            Response<List<Seat>> seatRes = seatCtx.getByRoom(tarRoom);
            System.out.println(seatRes.getStatus());
            if (seatRes.getStatus() == Response.OK) {
                
                req.setAttribute("rowLetters", MISC.alphabeticList(tarRoom.getNumRows()));
                System.out.println(tarRoom.getNumRows()+" " + tarRoom.getNumCols());
                req.setAttribute("seatMatrix", mapAsMatrixArray(tarRoom.getNumRows(), tarRoom.getNumCols(), seatRes.getReturnObject()));

            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        req.getRequestDispatcher("/views/manager/seat/seat_list.jsp").forward(req, resp);
    }

}
