@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalDate.class,
                value = LocalDateAdapter.class)
})

package eu.senla.hotel;

import eu.senla.hotel.utils.guest.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;