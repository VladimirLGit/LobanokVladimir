package eu.senla.task1.factory;

import eu.senla.task1.base.IProduct;
import eu.senla.task1.base.IProductPart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Product implements IProduct {
    private static final int MAX_VALUE = 0xFFFF;
    private Random RANDOM = new Random();
    private ProductPart[] productParts = new ProductPart[3];
    @Override
    public void installFirstPart(IProductPart iProductPart) {
        System.out.println("Собрали корпус машины");
        productParts[0] = (ProductPart) iProductPart;
        productParts[0].setSerialNumber(RANDOM.nextInt(MAX_VALUE));
    }

    @Override
    public void installSecondPart(IProductPart iProductPart) {
        System.out.println("Установили шасси на машину");
        productParts[1] = (ProductPart) iProductPart;
        productParts[1].setSerialNumber(RANDOM.nextInt(MAX_VALUE));
    }

    @Override
    public void installThridPart(IProductPart iProductPart) {
        System.out.println("Установили двигатель на машину");
        productParts[2] = (ProductPart) iProductPart;
        productParts[2].setSerialNumber(RANDOM.nextInt(MAX_VALUE));
    }

    @Override
    public String toString() {
        return "Product{" +
                "productParts=" + Arrays.toString(productParts) +
                '}';
    }
}
