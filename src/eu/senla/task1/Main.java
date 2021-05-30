// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

package eu.senla.task1;


import eu.senla.task1.factory.AssemblyLine;
import eu.senla.task1.factory.Product;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Product> arrayList = new ArrayList<>();
        AssemblyLine aLine = new AssemblyLine();
        for (int i = 0; i < 10; i++) {
            arrayList.add((Product) aLine.assemblyProduct(new Product()));
            System.out.println(arrayList.get(i).toString());
        }

        
    }
}