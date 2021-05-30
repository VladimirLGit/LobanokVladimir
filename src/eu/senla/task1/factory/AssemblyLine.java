package eu.senla.task1.factory;

import eu.senla.task1.base.IAssemblyLine;
import eu.senla.task1.base.IProduct;

public class AssemblyLine implements IAssemblyLine {
    private ProductPart[] parts = new ProductPart[3];
    void createPartCar(){
        parts[0] = new ProductPart(PartCar.BODY);
        parts[1] = new ProductPart(PartCar.CHASSIS);
        parts[2] = new ProductPart(PartCar.ENGINE);
    }
    @Override
    public IProduct assemblyProduct(IProduct iProduct) {
        createPartCar();
        iProduct.installFirstPart(parts[0]);
        iProduct.installSecondPart(parts[1]);
        iProduct.installThridPart(parts[2]);
        return iProduct;
    }
}
