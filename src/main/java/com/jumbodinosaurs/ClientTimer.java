package com.jumbodinosaurs;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ClientTimer extends Timer
{
    private static boolean stoped;
    public ClientTimer(int num, ActionListener listener)
    {
        super (num, listener);
        stoped = false;
    }

    public void start()
    {
        stoped = false;
        super.start();
    }

    public void stop()
    {
        stoped = true;
        super.stop();
    }

    //if true then not running
    public boolean getStatus()
    {
        return stoped;
    }

}
