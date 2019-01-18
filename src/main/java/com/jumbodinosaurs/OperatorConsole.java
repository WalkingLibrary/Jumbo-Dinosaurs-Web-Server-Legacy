package com.jumbodinosaurs;


import java.util.Scanner;

public class OperatorConsole implements Runnable
{
    private static final String[] commands = {"/?", "/help",// 0 1
            "/reinitphotos", "/cleardomains", // 2 3
            "/adddomain", "/editdomain", // 4 5
            "/stop", "/refreshsocket"};//6 7
    private static DataController dataIO;
    private static ServerControl serverControl;


    public OperatorConsole(DataController dataIO, ServerControl serverControl)
    {
        this.dataIO = dataIO;
        this.serverControl = serverControl;
    }

    public void run()
    {
        Scanner input = new Scanner(System.in);
        while (true)
        {
            String command = "";
            command += input.nextLine();
            command = command.trim().toLowerCase();

            if (command.length() >= 1 && command.substring(0, 1).equals("/"))
            {

                //Requesting Help
                if (command.contains(this.commands[0]) || command.contains(this.commands[1]))
                {
                    System.out.println("Commands: ");
                    for (String str : this.commands)
                    {
                        System.out.println(str);
                    }
                }
                //ReInit Photos
                else if (command.contains(this.commands[2]))
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
                else if (command.contains(commands[6]))
                {
                    System.out.println("Shutting Down");
                    System.exit(3);
                }
                else if (command.contains(commands[7]))
                {
                    serverControl.refreshSocket();
                }
                else
                {
                    System.out.println("Unrecognized command /help or /? for more Help." + "");
                }
            }
            else
            {
                System.out.println("Unrecognized command /help or /? for more Help." + "");
            }
        }
    }
}
