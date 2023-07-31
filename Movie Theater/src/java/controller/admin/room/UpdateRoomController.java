/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin.room;

import dal.dao.BranchDBContext;
import dal.dao.RoomDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Branch;
import model.Room;
import util.BaseDir;

/**
 *
 * @author CaoThuLuDau
 */
public class UpdateRoomController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Room roomToBeUpdated = new Room();
        roomToBeUpdated.setRoomID(Integer.parseInt(req.getParameter("roomID")));
        roomToBeUpdated.setRoomName(req.getParameter("roomName"));
        //roomToBeUpdated.setNoOfSeats(Integer.parseInt( req.getParameter("noOfSeats")) );
        
        Branch branch = new Branch();
        branch.setBranchID(Integer.parseInt( req.getParameter("branchID")) );
        roomToBeUpdated.setBranch(branch);
        
        RoomDBContext roomCtx = new RoomDBContext();
        roomCtx.update(roomToBeUpdated);
        resp.sendRedirect(BaseDir.connect(req, "admin/rooms"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("roomID").isEmpty()) {
            req.setAttribute("msg", "Please choose the room you want to update here");
            req.getRequestDispatcher("/views/admin/room/room_view.jsp").forward(req, resp);
        } else {
            BranchDBContext branchCtx = new BranchDBContext();
            List<Branch> branchList = branchCtx.all().getReturnObject();
            req.setAttribute("branchList", branchList);
            
            RoomDBContext roomCtx = new RoomDBContext();
            List<Room> roomList = roomCtx.all().getReturnObject();
            int roomID = Integer.parseInt(req.getParameter("roomID"));

            Room roomToBeUpdated = null;
            for (Room r : roomList) {
                if (r.getRoomID() == roomID) {
                    roomToBeUpdated = r;
                }
            }

            req.setAttribute("roomToBeUpdated", roomToBeUpdated);
            req.getRequestDispatcher("/views/admin/room/room_update.jsp").forward(req, resp);
        }

    }
}
