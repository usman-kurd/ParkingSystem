package com.example.parkingsystem.Queue;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.JsonDataConverter;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.example.parkingsystem.Objects.Registration;
import com.example.parkingsystem.Objects.parking;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import static com.example.parkingsystem.Controller.Controller.doRegistration;

public class sqsQueue {

    public static void main(String[] args) throws JsonProcessingException, SQLException, MessagingException, UnsupportedEncodingException {

//        Registration new_entry = new Registration("khalil","aziz","batool12547@gmail.com","33346661466",1,"GHJ-9876");
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(new_entry);
//        //SendRegistrationMessage(jsonString);
//        int size = getQueueSize("regis-queue");
//        System.out.println(size);
//       for(int i=0; i< size; i++)
//       {
//           Registration s = ReadRegistrationMessage("regis-queue");
//           System.out.println(s.toString());
//           int result = doRegistration(s);
//           System.out.println(result);
//           deleteMessages(s.receipt_handle);
//       }

//         parking park = new parking("ABC-1111","in");
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(park);
//
//        SendRegistrationMessage(jsonString);
//

    }
    static AmazonSQS sqs= AmazonSQSClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-2"))
            .build();

    static String queueUrl="http://localhost:4566/000000000000/regis-queue";
    static String queueUrl2="http://localhost:4566/000000000000/park-queue";


    public static String getQueueUrl(String queueName) {
        /* Getting Queue Url to send message */
        String queueUrl = String.valueOf(sqs.getQueueUrl(queueName));
        System.out.println(queueUrl.substring(11, queueUrl.length() - 1));
        queueUrl = queueUrl.substring(11, queueUrl.length() - 1);

        return queueUrl;
    }


    public static void SendMessage(String queueName,String msg){
        try
        {
            SendMessageRequest send_msg_req = new SendMessageRequest().withQueueUrl(getQueueUrl(queueName))
                    .withMessageBody(msg)
                    .withDelaySeconds(0);
            SendMessageResult send_msg_rslt = sqs.sendMessage(send_msg_req);
            System.out.println("Message Sent Successfully!");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
        }
    }


//    public static Registration ReadRegistrationMessage(String queueName) {
//
//        //Creating Receive Message Request
//        //ReceiveMessageRequest receive_msg_req = new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(1).withVisibilityTimeout(10);
//        ReceiveMessageRequest receive_msg_req=new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withVisibilityTimeout(10).withWaitTimeSeconds(10);
//        //Getting a list of messages
//        List<Message> messages = sqs.receiveMessage(receive_msg_req).getMessages();
//        //Printing the size of Queue
//        System.out.println(messages.size());
//        if(messages.size()!=0) {
//            //Printing Message
//            System.out.println(messages.get(0).getBody());
//            System.out.println(messages.get(0).toString());
//            //return messages.get(0).getReceiptHandle();
//            Registration reg =null;
//            try
//            {
//                String json= messages.get(0).getBody();
//                Gson g = new Gson();
//
//
//                //String ne = json.substring(12,json.length());
//                String ne = messages.get(0).getBody();
//                reg= g.fromJson(ne,Registration.class);
//
//                reg.setReceipt_handle(messages.get(0).getReceiptHandle());
//
//                System.out.println("now after removed "+ ne);
//
//                return reg;
//            }
//            catch(Exception ex)
//            {
//                ex.printStackTrace();
//                System.out.println("exception:" + reg);
//                //return messages.get(0).getBody();
//                return reg;
//            }
//
//        }
//        else
//        {
//            System.out.println("Error; Cant read the top most message as the queue is empty");
//        }
//        System.out.println("kim");
//        return null;
//    }
public static Object ReadMessage(String queueName)
{

    ReceiveMessageRequest receive_msg_req=new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withVisibilityTimeout(10).withWaitTimeSeconds(10);

    List<Message> messages = sqs.receiveMessage(receive_msg_req).getMessages();

    System.out.println(messages.size());
    if(messages.size()!=0)
    {

        System.out.println(messages.get(0).getBody());
        System.out.println(messages.get(0).toString());

        if(queueName == "regis-queue")
        {
            Registration reg =null;
            try
            {
                String json= messages.get(0).getBody();
                Gson g = new Gson();
                String ne = messages.get(0).getBody();
                reg= g.fromJson(ne,Registration.class);
                reg.setReceipt_handle(messages.get(0).getReceiptHandle());
                System.out.println("now after removed "+ ne);
                return reg;
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                System.out.println("exception:" + reg);
                //return messages.get(0).getBody();
                return reg;
            }
        }
        else
        {
            parking par = null;
            try
            {
                String json= messages.get(0).getBody();
                Gson g = new Gson();
                String ne = messages.get(0).getBody();
                par= g.fromJson(ne,parking.class);
                par.setReceiptHandle(messages.get(0).getReceiptHandle());
                System.out.println("now after removed "+ ne);
                return par;
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                System.out.println("exception:" + par);
                //return messages.get(0).getBody();
                return par;
            }
            }
    }
    else
    {
        System.out.println("Error; Cant read the top most message as the queue is empty");
    }
    System.out.println("kim");
    return null;
}

    public static int getQueueSize(String queueName)
    {
        //Creating Receive Message Request
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest().withQueueUrl(getQueueUrl(queueName)).withMaxNumberOfMessages(30).withVisibilityTimeout(1);
        //Getting a list of messages
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        //Printing the size of Queue
        return messages.size();
    }

    public static void deleteMessages(String queueName,String receiptHandle)
    {
        sqs.deleteMessage(getQueueUrl(queueName),receiptHandle);
        System.out.println("messages has been successfully deleted");
    }
}


