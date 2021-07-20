package eu.senla.multithread.task3;

import java.util.List;

import static java.lang.Thread.sleep;

public class ExtractThread implements Runnable{

    private List<Integer> arrayInt;
    public ExtractThread(List<Integer> listInt) {
        this.arrayInt = listInt;
    }

    private synchronized void extractNumber() {
        Integer integer = arrayInt.get(0);
        arrayInt.remove(0);
        System.out.println(Thread.currentThread().getName() + " Extract number - " + integer);
    }

    @Override
    public void run() {
        while (true) {
            if (arrayInt.size() > 0) {
                extractNumber();
            }
            else {
                try {
                    Thread.currentThread().join(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ;

        }
    }
}
