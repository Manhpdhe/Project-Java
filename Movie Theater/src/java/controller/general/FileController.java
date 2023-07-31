/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.general;

import cfg.MyConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class FileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("filename");
        if (filename == null) {
            Map<String,String> envs =  System.getenv();
            resp.getWriter().println("ENVIRONMENT VARIABLES");
            for (Map.Entry<String, String> entry : envs.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                resp.getWriter().println(key + " : " + value);
    // do something with the key and value
            }
            resp.getWriter().println("Upload location: " + req.getAttribute("uploadDir"));
        } else {
            File file = new File(MyConfig.retrieve().getUploadLocation(), filename);
        // Canonical path is used to prevent directory traversal
        if (file.getCanonicalPath().startsWith(MyConfig.retrieve().getUploadLocation())) {
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", String.format("filename=\"%s\"",filename));
            Files.copy(file.toPath(), resp.getOutputStream());
        } else {
            resp.getWriter().println("Invalid file parse!");
        } 
        }
        
    }
    
}
