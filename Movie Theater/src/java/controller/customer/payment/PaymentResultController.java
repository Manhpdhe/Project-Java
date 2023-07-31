/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.customer.payment;

import cfg.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import util.JobInstance;
import dal.dao.TicketHistoryDBContext;
import model.Response;
import model.TicketHistory;

/**
 *
 * @author Admin
 */
public class PaymentResultController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map fields = new HashMap();
        for (Enumeration params = req.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(req.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = req.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);

        JobInstance ji = (JobInstance) req.getSession().getAttribute("focusedTransaction");
        if (signValue.equals(vnp_SecureHash)) {
            TicketHistoryDBContext tbHisCtx = new TicketHistoryDBContext();
            if ("00".equals(req.getParameter("vnp_TransactionStatus"))) {
                Response<TicketHistory> thRes = tbHisCtx.byOrderCode(fields.get("vnp_TxnRef").toString());
                if (thRes.getStatus() == Response.OK) {
                    TicketHistoryDBContext tbHisCtxUp = new TicketHistoryDBContext();
                    TicketHistory newHis = thRes.getReturnObject();
                    newHis.setPaymentStatus(1);
                    tbHisCtxUp.updateStatus(newHis);
                }
                req.setAttribute("success", true);
            } else {
                Response<TicketHistory> thRes = tbHisCtx.byOrderCode(fields.get("vnp_TxnRef").toString());
                if (thRes.getStatus() == Response.OK) {
                    TicketHistoryDBContext tbHisCtxUp = new TicketHistoryDBContext();
                    TicketHistory newHis = thRes.getReturnObject();
                    newHis.setPaymentStatus(3);
                    tbHisCtxUp.updateStatus(newHis);
                }
                try {
                    JobKey jk = new JobKey(String.format("%s_Job",req.getParameter("vnp_TxnRef")),"group1");
                    JobInstance.execute(jk);
                } catch (Exception e) {

                }

                req.setAttribute("success", false);
            }

        } else {
            req.setAttribute("errormsg", "Invalid signature");
        }

        try {
            
            JobKey jk = new JobKey(String.format("%s_Job",req.getParameter("vnp_TxnRef")),"group1");
            TriggerKey tk = new TriggerKey(String.format("%s_Trigger",req.getParameter("vnp_TxnRef")),"group1");
            JobInstance.kill(jk,tk);
        } catch (Exception e) {

        }
        System.out.println("DCMMMMMMMMMMM");

        req.getRequestDispatcher("/views/customer/payment_result.jsp").forward(req, resp);

    }

}
