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
public class InsertRoomController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        
        //Get all data from the insert form (insertroom.jsp)
        String roomName = req.getParameter("roomname");
        int noOfSeats = Integer.parseInt(req.getParameter("noOfSeats"));
        int branchID = Integer.parseInt(req.getParameter("branchID"));

        Room room = new Room();
        room.setRoomName(roomName);
        //room.setNoOfSeats(noOfSeats);
        
        Branch branch = new Branch();
        branch.setBranchID(branchID);
        room.setBranch(branch);
        
        RoomDBContext roomCtx = new RoomDBContext();
        roomCtx.insert(room);
        resp.sendRedirect(BaseDir.connect(req, "admin/rooms"));
        
    }      
    
        @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Room Insert page redirect
        BranchDBContext branchCtx = new BranchDBContext();
        // Assumes the retrieval goes successfully
        resp.sendRedirect(BaseDir.connect(req, "admin/rooms"));
    }
}
