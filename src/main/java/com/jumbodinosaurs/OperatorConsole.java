package com.jumbodinosaurs;

import java.util.Scanner;

public class OperatorConsole implements Runnable
{
    private static DataController dataIO;
    private static final String[] commands = {"/?", "/help" ,"/reInitPhotos", "/cleardomains", "/adddomain", "/editdomain"};

    public OperatorConsole(DataController dataIO)
    {
        this.dataIO = dataIO;
    }

    public void run()
    {
        Scanner input = new Scanner(System.in);
        while(true)
        {
            String command = input.nextLine();
            if(command.substring(0, 1).equals("/"))
            {
                if(command.contains(this.commands[0]) || command.contains(this.commands[1]))
                {
                    System.out.println("Commands: ");
                    for(String str: this.commands)
                    {
                        System.out.println(str);
                    }
                }
                else if(command.contains(this.commands[2]))
                {
                    try
                    {
                        Thread initThread = new Thread(this.dataIO);
                        initThread.start();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("Error Initializing Pictures");
                    }
                }
                else
                {
                    System.out.println("Unrecognized command /help or /? for more Help." +
                            "");
                }
            }
        }
    }
}
