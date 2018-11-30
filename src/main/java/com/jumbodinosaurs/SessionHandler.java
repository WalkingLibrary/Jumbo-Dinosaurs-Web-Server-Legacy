package com.jumbodinosaurs;

public class SessionHandler implements Runnable
{
    private static DataController dataIO;

    private Session session;

    public SessionHandler(Session session, DataController dataIO)
    {
        this.dataIO = dataIO;
        this.session = session;
    }

    public void run()
    {
        HTTPRequest request = new HTTPRequest(this.session.getMessage());
        if(request.isHTTP())
        {
            request.generateMessage(this.dataIO);
        }
        else
        {
            request.setMessage501(this.dataIO);
        }

        //Send Message
        try
        {
            System.out.println("Message Sent to Client: \n" + request.getMessageToSend());
            this.session.send(request.getMessageToSend());
            if(request.isPictureRequest())
            {
                this.session.send(request.getPictureContents());
            }
            this.session.flush();
            this.session.close();
            this.session.setMessageSent(request.getMessageToSend());
            System.out.println("Adding Session to Logger");
            this.dataIO.log(this.session);
            System.out.println("Session Complete");
        }
        catch (Exception e)
        {
            System.out.println("Error Sending Message");
            e.printStackTrace();
            System.out.println(e.getCause());
        }
        System.out.println("Thread Ended");
    }
}
