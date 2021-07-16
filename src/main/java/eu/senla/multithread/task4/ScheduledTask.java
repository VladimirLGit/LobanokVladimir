package main.java.eu.senla.multithread.task4;

import java.util.Date;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    Date now;
    @Override
    public void run() {
        now = new Date();
        System.out.println("Текущая дата и время : " + now);
    }

}