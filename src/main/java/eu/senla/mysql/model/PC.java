package eu.senla.mysql.model;

import java.sql.Date;

public class PC {
    private Integer code;
    private String model;
    private Short speed;
    private Short ram;
    private Float hd;
    private String cd;
    private Integer price;

    public String getModel() {
        return model;
    }

    public int getSpeed() {
        return speed;
    }
}
