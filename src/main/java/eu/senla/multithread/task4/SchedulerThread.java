package eu.senla.multithread.task4;

import java.util.Timer;

public class SchedulerThread {
    public static void main(String args[]) throws InterruptedException {

        Timer time = new Timer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 3000); // Создаем задачу с повторением через 1 сек.

        for (int i = 0; i <= 5; i++) {
            Thread.sleep(6000);
            System.out.println("Execution in Main Thread. #" + i);
            if (i == 5) {
                System.out.println("Application Terminates");
                System.exit(0);
            }
        }
    }
}
