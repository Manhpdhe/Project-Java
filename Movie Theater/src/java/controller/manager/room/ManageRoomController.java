/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.manager.room;

import dal.dao.BranchDBContext;
import dal.dao.CinemaDBContext;
import dal.dao.RoomDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Branch;
import model.Cinema;
import model.Room;
import util.BaseDir;

/**;'
 *
 * @author CaoThuLuDau
 */
public class ManageRoomController extends HttpServlet {

    private RoomDBContext roomDBContext = new RoomDBContext();
    private CinemaDBContext cinemaDBContext = new CinemaDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (null == action) {
            //Set BranchID
            String branchID = request.getParameter("branchID");
            Cinema cinema = cinemaDBContext.getByBranchID(request.getParameter("branchID")).getReturnObject();
            request.setAttribute("cinema", cinema);
            
            //Set Branch Room
            List<Room> rooms = roomDBContext.getByBranchID(branchID).getReturnObject();
            request.setAttribute("rooms", rooms);
            
            //Set Number of seats info
            Map<Integer, Integer> roomSeats = new HashMap<>();
            for (Room room : rooms) {
                Integer noOfSeats = roomDBContext.getNumberOfSeatsInRoom(room.getRoomID()).getReturnObject();
                roomSeats.put(room.getRoomID(), noOfSeats);
            }
            request.setAttribute("roomSeats", roomSeats);
            request.getRequestDispatcher("/views/manager/room/room_view.jsp").forward(request, response);
        } else {
            switch (action) {
//                case "delete":
//                    int roomID = Integer.parseInt(request.getParameter("roomID"));
//
//                    List<Room> roomList = roomDBContext.all().getReturnObject();
//                    Room roomToBeDeleted = null;
//                    for (Room r : roomList) {
//                        if (r.getRoomID() == roomID) {
//                            roomToBeDeleted = r;
//                        }
//                    }
//                    roomDBContext.delete(roomToBeDeleted);
//
//                    roomList = roomDBContext.all().getReturnObject();
//                    request.setAttribute("roomList", roomList);
//                    request.getRequestDispatcher("/view/manage/room/room_view.jsp").forward(request, response);
//                    break;
//                case "update":
//                    if (request.getParameter("roomID").isEmpty()) {
//                        request.setAttribute("msg", "Please choose the room you want to update here");
//                        request.getRequestDispatcher("/views/manage/room/room_view.jsp").forward(request, response);
//                    } else {
//                        BranchDBContext branchCtx = new BranchDBContext();
//                        List<Branch> branchList = branchCtx.all().getReturnObject();
//                        request.setAttribute("branchList", branchList);
//
//                        RoomDBContext roomCtx = new RoomDBContext();
//                        List<Room> roomLis = roomCtx.all().getReturnObject();
//                        int roomI = Integer.parseInt(request.getParameter("room-id"));
//
//                        Room roomToBeUpdated = null;
//                        for (Room r : roomLis) {
//                            if (r.getRoomID() == roomI) {
//                                roomToBeUpdated = r;
//                            }
//                        }
//
//                        request.setAttribute("roomToBeUpdated", roomToBeUpdated);
//                        request.getRequestDispatcher("/views/manage/room/room_update.jsp").forward(request, response);
//                    }
//                    break;
                case "create":
                    response.sendRedirect(BaseDir.connect(request, "headmanage/rooms?action=create"));
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
//        if (action.equals("update")) {
//            Room roomToBeUpdated = new Room();
//            roomToBeUpdated.setRoomID(Integer.parseInt(request.getParameter("room-id")));
//            roomToBeUpdated.setRoomName(request.getParameter("room-name"));
//            roomToBeUpdated.setNumRows(Integer.parseInt(request.getParameter("num-rows")));
//            roomToBeUpdated.setNumCols(Integer.parseInt(request.getParameter("num-cols")));
//
//            Branch branch = new Branch();
//            branch.setBranchID(Integer.parseInt(request.getParameter("branch-id")));
//            roomToBeUpdated.setBranch(branch);
//
//            RoomDBContext roomCtx = new RoomDBContext();
//            roomCtx.update(roomToBeUpdated);
//            response.sendRedirect(BaseDir.connect(request, "headmanage/rooms"));
//
//        } else 
            if (action.equals("create")) {
            response.setContentType("text/html;charset=UTF-8");

            //Get all data from the insert form (insertroom.jsp)
            String roomName = request.getParameter("room-name");
            int numRows = Integer.parseInt(request.getParameter("num-rows"));
            int numCols = Integer.parseInt(request.getParameter("num-cols"));
            int branchID = Integer.parseInt(request.getParameter("branch-id"));

            Room room = new Room();
            room.setRoomName(roomName);
            room.setNumRows(numRows);
            room.setNumCols(numCols);

            Branch branch = new Branch();
            branch.setBranchID(branchID);
            room.setBranch(branch);

            if (roomDBContext.insert(room).getReturnObject().get("inserted")) {
                request.setAttribute("msg", "Create room successfully");
            } else {
                request.setAttribute("msg", "Create room fail");
            }   
            response.sendRedirect(BaseDir.connect(request, "headmanage/rooms?branchID=" + branchID));
        }
    }
}
