package eu.senla.mysql.model;

import java.sql.Date;

public class PC {
    private Integer code;
    private String model;
    private Integer speed;
    private Double ram;
    private Double hd;
    private Integer cd;
    private Integer price;

    public String getModel() {
        return model;
    }

    public int getSpeed() {
        return speed;
    }

    public double getRam() {
        return ram;
    }

    public double getHd() {
        return hd;
    }

    public int getCd() {
        return cd;
    }

    public Integer getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRam(Double ram) {
        this.ram = ram;
    }

    public void setHd(Double hd) {
        this.hd = hd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
