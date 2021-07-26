package eu.senla.mysql.model;

public class Printer {
    private Integer code;
    private String model;
    private String maker;
    private Byte color;
    private String type;
    private Integer price;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

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

    public Byte getColor() {
        return color;
    }

    public void setColor(Byte color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Printer{" +
                "code=" + code +
                ", model='" + model + '\'' +
                ", maker='" + maker + '\'' +
                ", color=" + color +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
