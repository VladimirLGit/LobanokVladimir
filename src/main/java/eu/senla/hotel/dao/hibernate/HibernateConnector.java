package eu.senla.hotel.dao.hibernate;

import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Room;
import eu.senla.hotel.model.ServiceOrder;
import eu.senla.hotel.model.links.ItemTable;
import eu.senla.hotel.model.links.LinkGuest;
import eu.senla.hotel.model.links.LinkService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateConnector {
    private static HibernateConnector me;
    private Configuration cfg;
    private SessionFactory sessionFactory;

    private HibernateConnector() throws HibernateException {

        cfg = new Configuration().configure();
        cfg.addAnnotatedClass(Guest.class);
        cfg.addAnnotatedClass(Room.class);
        cfg.addAnnotatedClass(ServiceOrder.class);

        cfg.addAnnotatedClass(ItemTable.class);
        cfg.addAnnotatedClass(LinkGuest.class);
        cfg.addAnnotatedClass(LinkService.class);
        ServiceRegistry servReg = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        sessionFactory = cfg.buildSessionFactory(servReg);

        //ServiceRegistry servReg = new StandardServiceRegistryBuilder()
        //        .configure()
        //        .build();
        //sessionFactory = new MetadataSources(servReg).buildMetadata().buildSessionFactory();
        //cfg = new Configuration().configure();
        //sessionFactory = cfg.buildSessionFactory( servReg );
    }

    public static synchronized HibernateConnector getInstance() throws HibernateException {
        if (me == null) {
            me = new HibernateConnector();
        }
        return me;
    }

    public Session getSession() throws HibernateException {
        Session session = sessionFactory.openSession();
        if (!session.isConnected()) {
            this.reconnect();
        }
        return session;
    }

    private void reconnect() throws HibernateException {
        this.sessionFactory = cfg.buildSessionFactory();
    }
}
