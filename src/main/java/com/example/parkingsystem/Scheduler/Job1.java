package com.example.parkingsystem.Scheduler;

import com.example.parkingsystem.localDatabase.Database;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.parkingsystem.Notification.notification.sendEmail;

//import static com.example.parkingsystem.notification.sendEmail;

public class Job1 implements Job
{
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
//        try
//        {
        Database b = null;
        try {
            b = new Database();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert b != null;
        ArrayList<String> array = b.checkAt1am();
        System.out.println("retured vro "+ array);

        for(String i : array )
        {
            System.out.println("reached here");
            System.out.println("get "+ i);

            try
            {
                sendEmail(i,"1 am Alert","park your car");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        //System.exit(0);
//        }
//        catch(Exception ex)
//        {
//            ex.printStackTrace();
//        }

    }
}
