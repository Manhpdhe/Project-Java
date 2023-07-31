/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin.room;

import dal.dao.RoomDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Room;
import util.BaseDir;

/**
 *
 * @author CaoThuLuDau
 */
public class DeleteRoomController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int roomID = Integer.parseInt(req.getParameter("roomID"));
        
        RoomDBContext roomCtx = new RoomDBContext();
        List<Room> roomList = roomCtx.all().getReturnObject();
        Room roomToBeDeleted = null;
        for (Room r : roomList) {
            if (r.getRoomID() == roomID) {
                roomToBeDeleted = r;
            }
        }
        roomCtx.delete(roomToBeDeleted);
        
        roomList = roomCtx.all().getReturnObject();
        req.setAttribute("roomList", roomList);
        req.getRequestDispatcher("/views/admin/room/room_view.jsp").forward(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoomDBContext roomCtx = new RoomDBContext();
        List<Room> roomList = roomCtx.all().getReturnObject();
        int roomID = Integer.parseInt(req.getParameter("roomID"));
        
        
        Room roomToBeDeleted = null;
        for (Room r : roomList) {
            if (r.getRoomID() == roomID) {
                roomToBeDeleted = r;
            }
        }
        
        roomCtx.delete(roomToBeDeleted);
        resp.sendRedirect(BaseDir.connect(req, "admin/rooms"));
    }
}
