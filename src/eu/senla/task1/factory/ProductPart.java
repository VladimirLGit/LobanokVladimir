package eu.senla.task1.factory;

import eu.senla.task1.base.IProductPart;

public class ProductPart implements IProductPart {
    private PartCar ePart;
    private int serialNumber;

    public ProductPart(PartCar ePart) {
        this.ePart = ePart;
        this.serialNumber = 00000;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return    "ePart=" + ePart +
                ", serialNumber=" + serialNumber;
    }
}
