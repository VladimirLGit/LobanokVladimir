package eu.senla.Hotel.model;

public class Service {
    private int idService;
    private String nameService;
    private int priceService;
    public Service(String nameService, int priceService) {
        this.nameService = nameService;
        this.priceService = priceService;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getPriceService() {
        return priceService;
    }

    public void setPriceService(int priceService) {
        this.priceService = priceService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "nameService='" + nameService + '\'' +
                ", priceService=" + priceService +
                '}';
    }
}
