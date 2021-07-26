package eu.senla.mysql.model;

import java.sql.Date;

public class PC {
    private Integer code;
    private String model;
    private String maker;
    private Integer speed;
    private Double ram;
    private Double hd;
    private Integer cd;
    private Integer price;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(Double ram) {
        this.ram = ram;
    }

    public double getHd() {
        return hd;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PC{" +
                "code=" + code +
                ", model='" + model + '\'' +
                ", maker='" + maker + '\'' +
                ", speed=" + speed +
                ", ram=" + ram +
                ", hd=" + hd +
                ", cd=" + cd +
                ", price=" + price +
                '}';
    }
}
