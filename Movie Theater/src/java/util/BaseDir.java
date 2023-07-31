/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tien Dung
 */
public class BaseDir {

    /**
     * author: Tien Dung purpose: To get files with the project folder as the
     * base directory create date: 12-5-2023 (DD-MM-YYYY)
     */
    public static String connect(HttpServletRequest req, String filePath) {
        String baseDir = req.getContextPath();
        System.out.println(baseDir);
        return baseDir + "/" + filePath;
    }
    
    public static String connectFullUrl(HttpServletRequest req, String filePath) {
        String baseDir = req.getContextPath();
        String baseUrl = req.getRequestURL().toString();
        return baseUrl.substring(0,baseUrl.indexOf(baseDir)+baseDir.length())+"/"+filePath;
    }

    public static String connect(HttpServletRequest req, String filePath, Map<String, String[]> params) {
        String baseDir = req.getContextPath();
        String queryParamStr = "";
        System.out.println(baseDir);
        try {
            List fieldNames = new ArrayList(params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String[] fieldValues = (String[]) params.get(fieldName);
                if ((fieldValues != null) && (fieldValues.length > 0)) {
                    //Build query
                    int i = 0;
                    for (String field : fieldValues) {
                        i++;
                        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                        query.append('=');
                        query.append(URLEncoder.encode(field, StandardCharsets.US_ASCII.toString()));
                        
                        if (i < fieldValues.length) {
                            // This is the last element
                            query.append('&');
                        }
                    }
                    if (itr.hasNext()) {
                        query.append('&');
                    }
                    
                }
            }
            queryParamStr = query.toString();
        }
        catch (Exception e) {
            
        }

        return baseDir + "/" + filePath + (queryParamStr != "" ? "?" : "") + queryParamStr;
    }

    public static String getBaseWeb(HttpServletRequest req) {
        StringBuffer url = req.getRequestURL();
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String base = url.substring(0, url.length() - uri.length() + ctx.length());
        return base;
    }
}
