package com.driver;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Gmail extends Email {

    int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)

    Queue<Mail> inbox=new LinkedList<>();
    ArrayList<Mail> trash=new ArrayList<>();
    public Gmail(String emailId, int inboxCapacity) {
            super(emailId);
            this.inboxCapacity=inboxCapacity;
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if(inbox.size()>=inboxCapacity){
            Mail m=inbox.remove();
            trash.add(m);

        }
        inbox.add(new Mail(date,sender,message));

    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        Queue<Mail> temp=new LinkedList<>();
        while(inbox.isEmpty()==false){
            Mail m=inbox.remove();
            if(m.getMessage().equals(message)){
                trash.add(m);
                break;
            }
            temp.add(m);

        }

        while(inbox.isEmpty()==false){
            Mail m=inbox.remove();
            temp.add(m);
        }

        while(temp.isEmpty()==false){
            Mail m=temp.remove();
            inbox.add(m);
        }
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inbox.isEmpty()==true){
            return "";
        }
        Queue<Mail> temp=new LinkedList<>();
        while(inbox.size()!=1){
            Mail m=inbox.remove();
            temp.add(m);
        }
        Mail latest=inbox.remove();
        String res=latest.getMessage();
        temp.add(latest);
        while(temp.isEmpty()==false){
            Mail m=temp.remove();
            inbox.add(m);
        }
        return res;
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inbox.isEmpty()==true){
            return "";
        }
        Mail oldest=inbox.peek();
        return oldest.getMessage();

    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count=0;
        Queue<Mail> temp=new LinkedList<>();
        while(inbox.isEmpty()==false){
            Mail m=inbox.remove();
            if(m.getDate().compareTo(start)>=0 && m.getDate().compareTo(end)<=0){
                count++;
            }
            temp.add(m);

        }



        while(temp.isEmpty()==false){
            Mail m=temp.remove();
            inbox.add(m);
        }
        return count;
    }

    public int getInboxSize(){
        // Return number of mails in inbox
         return inbox.size();
    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trash.size();

    }

    public void emptyTrash(){
        // clear all mails in the trash
       trash.clear();
    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return this.inboxCapacity;
    }
}
