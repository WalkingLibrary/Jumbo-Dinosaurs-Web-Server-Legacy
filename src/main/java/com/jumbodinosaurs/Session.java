package com.jumbodinosaurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

public class Session
{
    private String who;
    private String when;
    private String message;
    private String messageSent;
    private transient DataOutputStream output;
    private transient InputStream input;
    private transient ClientTimer timeOut = new ClientTimer(2500, new ComponentsListener());


    public Session(Socket client) throws Exception
    {
        this.who = client.getInetAddress().getHostAddress();
        this.when = new Date().toString();
        this.output = new DataOutputStream(client.getOutputStream());
        this.input = client.getInputStream();

        //for reading message from client
        String line = "";
        this.message = "";
        int temp;


        //If there is info to read
        this.timeOut.start();//To avoid one request takeing way to long
        while (this.input.available() > 0 || this.message.equals("") && !this.timeOut.getStatus())
        {
            temp = this.input.read();
            line = "" + (char) temp;
            this.message += line;
        }
        System.out.println("Message From Client: \n" + this.message);
    }

    public Session(String who, String when, String message, String messageSent)
    {
        this.who = who;
        this.when = when;
        this.message = message;
        this.messageSent = messageSent;
    }

    public void sendByteArray(byte[] bytes) throws Exception
    {
        this.output.write(bytes);
    }

    public String getWho()
    {
        return this.who;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessageSent(String messageSent)
    {
        this.messageSent = messageSent;
    }

    public String getWhen()
    {
        return this.when;
    }

    public void flush() throws Exception
    {
        this.output.flush();
    }

    public void send(String message) throws Exception
    {
        this.output.writeBytes(message);
        this.output.flush();
    }

    public void close() throws Exception
    {
        this.output.close();
        this.input.close();
    }

    private class ComponentsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource().equals(timeOut))
            {
                timeOut.stop();
            }
        }
    }


}
