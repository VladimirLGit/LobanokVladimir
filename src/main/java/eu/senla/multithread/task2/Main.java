// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

package eu.senla.multithread.task2;


import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        /* create Runnable using anonymous inner class */
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 15; i++) {
                System.out.println("starting expensive task thread " + Thread.currentThread().getName());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 15; i++) {
                System.out.println("starting expensive task thread " + Thread.currentThread().getName());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.setName("thread1");
        t2.setName("thread2");

        for(int i = 0; i < 15; i++) {
                if (t1.getState() == Thread.State.NEW)
                    t1.start();
                if (t2.getState() == Thread.State.NEW)
                    t2.start();
            try {
                if (t1.isAlive())
                    t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




        }
    }
}