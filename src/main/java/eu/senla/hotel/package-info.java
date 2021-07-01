@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalDate.class,
                value = LocalDateAdapter.class)
})

package main.java.eu.senla.hotel;

import main.java.eu.senla.hotel.utils.guest.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;