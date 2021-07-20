package eu.senla.multithread.task3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Integer> number = new ArrayList<>();
    public static void main(String[] args) {

        InsertThread insert = new InsertThread(number);
        ExtractThread extract = new ExtractThread(number);
        Thread t1 = new Thread(insert);
        Thread t2 = new Thread(extract);
        t1.setName("insert");
        t2.setName("extract");

        t1.start();
        t2.start();
    }
}
