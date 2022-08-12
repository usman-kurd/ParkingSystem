package com.example.parkingsystem.Scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Scheduler extends HttpServlet
{
//    public static void main(String[] args) throws SchedulerException
//    {
        //Scheduler s = new Scheduler();
        //s.run();
       // run();
//    }

//    public static void run() throws SchedulerException {

//    public Scheduler() throws SchedulerException {

     public void init() throws ServletException{
      try {
        JobDetail job1 = JobBuilder.newJob(Job1.class).withIdentity("Iterate", "group1").build();

        Trigger trigger1 = TriggerBuilder.newTrigger()
                .withIdentity("triggerIterate", "group1")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(15, 42))
                .build();
        org.quartz.Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
        scheduler1.start();
        scheduler1.scheduleJob(job1, trigger1);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}



