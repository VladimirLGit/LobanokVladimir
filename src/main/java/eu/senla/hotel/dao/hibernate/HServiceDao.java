package eu.senla.hotel.dao.hibernate;

import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.Service;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@Component
public class HServiceDao implements IServiceDao {
    @Override
    public void addService(Service service) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            transaction = session.beginTransaction();
            session.save(service);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteService(Service service) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Transaction beginTransaction = session.beginTransaction();
            Query createQuery = session.createQuery("delete from Services s where s.idService =:id");
            createQuery.setParameter("id", service.getId());
            createQuery.executeUpdate();
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateService(Service service) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            session.saveOrUpdate(service);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Service> allServices() {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query query = session.createQuery("from Services s");

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<Service>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void addOrderGuest(Guest guest, Service service) {

    }

    @Override
    public void deleteOrderGuest(Guest guestOut) throws NotExistObject {

    }

    @Override
    public Service readService(Integer idService) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query query = session.createQuery("from Services s where s.idService = :id");
            query.setParameter("id", idService);

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (Service) queryList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void setServices(List<Service> services) {

    }
}
