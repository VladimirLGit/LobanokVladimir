package eu.senla.multithread.task3;

import java.util.List;
import java.util.Random;

public class InsertThread implements Runnable {
    private Random RANDOM = new Random();
    private List<Integer> arrayInt;

    public InsertThread(List<Integer> listInt) {
        this.arrayInt = listInt;
    }

    private synchronized void insertNumber() {
        Integer integer = 1 + RANDOM.nextInt(15);
        arrayInt.add(integer);
        System.out.println(Thread.currentThread().getName() + " Insert number - " + integer);
    }

    @Override
    public void run() {
        while (true) {
            if (arrayInt.size() < 2) {
                insertNumber();
            }
            else {
                try {
                    Thread.currentThread().join(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
