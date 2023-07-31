/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job;

import dal.dao.TicketDBContext;
import java.util.Map;
import model.Order;
import model.Response;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 *
 * @author Admin
 */
public class PaymentTimeoutJob implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        JobDataMap dataMap = jec.getJobDetail().getJobDataMap();
        Order order = (Order) dataMap.get("order");
        TicketDBContext ticketCtx = new TicketDBContext();
        Response<Map<String,Boolean>> res = ticketCtx.delete(order);
        if (res.getStatus() == Response.OK) {
            System.out.println("Successfully deleted!");
        }
        
    }
    
}
