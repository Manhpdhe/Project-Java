/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin.room;

import dal.dao.BranchDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dal.dao.RoomDBContext;
import java.util.List;
import model.Branch;
import model.Room;

/**
 *
 * @author CaoThuLuDau
 */
public class ViewRoomController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoomDBContext roomCtx = new RoomDBContext();
        List<Room> roomList = roomCtx.all().getReturnObject();
        // Room Insert page redirect
        BranchDBContext branchCtx = new BranchDBContext();
        List<Branch> branchList = branchCtx.all().getReturnObject();
        req.setAttribute("branchList", branchList);
        req.setAttribute("roomList", roomList);
        req.getRequestDispatcher("/views/admin/room/room_view.jsp").forward(req, resp);
    }
}
