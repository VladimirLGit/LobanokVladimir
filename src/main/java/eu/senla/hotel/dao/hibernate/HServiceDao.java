package eu.senla.hotel.dao.hibernate;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.ServiceOrder;
import eu.senla.hotel.model.links.LinkService;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Component
public class HServiceDao implements IServiceDao {
    @Override
    public void addService(ServiceOrder serviceOrder) {
        Session session = null;
        Transaction transaction;
        try {
            session = HibernateConnector.getInstance().getSession();
            transaction = session.beginTransaction();
            session.save(serviceOrder);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteService(ServiceOrder serviceOrder) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Transaction beginTransaction = session.beginTransaction();
            Query createQuery = session.createQuery("delete from Services s where s.idService =:id");
            createQuery.setParameter("id", serviceOrder.getId());
            createQuery.executeUpdate();
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateService(ServiceOrder serviceOrder) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Transaction beginTransaction = session.beginTransaction();
            session.saveOrUpdate(serviceOrder);
            session.flush();
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<ServiceOrder> allServices() {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query query = session.createQuery("from Services s");

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<ServiceOrder>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void addOrderGuest(Guest guest, ServiceOrder serviceOrder) {
        LinkService linkService = new LinkService(serviceOrder.getId(), guest.getId());
        Session session = null;
        Transaction transaction;
        try {
            session = HibernateConnector.getInstance().getSession();
            transaction = session.beginTransaction();
            session.save(linkService);
            transaction.commit();
            //guest.addOrderedService(service);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteOrderGuest(Guest guestOut) throws NotExistObject {

    }

    @Override
    public ServiceOrder readService(Integer idService) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query query = session.createQuery("from Services s where s.idService = :id");
            query.setParameter("id", idService);

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (ServiceOrder) queryList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void setServices(List<ServiceOrder> serviceOrders) {

    }
}
