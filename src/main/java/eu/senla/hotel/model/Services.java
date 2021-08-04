package eu.senla.hotel.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "hotelService")
@XmlRootElement
public class Services {

    @XmlElement(name = "Service")
    private List<Service> servicesList;

    public List<Service> getServices() {
        return servicesList;
    }

    public void setServices(List<Service> services) {
        this.servicesList = services;
    }
}
