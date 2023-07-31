/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Admin
 */
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerSingleton {
    private static Scheduler instance = null;

    private SchedulerSingleton() {}

    public static synchronized Scheduler getInstance() throws SchedulerException {
        if (instance == null) {
            instance = new StdSchedulerFactory().getScheduler();
        }
        return instance;
    }
}
