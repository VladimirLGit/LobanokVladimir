package eu.senla.hotel.dao.hibernate;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HGuestDao implements IGuestDao {
    @Override
    public boolean addGuest(Guest guest) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            transaction = session.beginTransaction();
            session.save(guest);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteGuest(Guest guest) throws NotExistObject {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Transaction beginTransaction = session.beginTransaction();
            Query createQuery = session.createQuery("delete from Guests g where g.idGuest =:id");
            createQuery.setParameter("id", guest.getId());
            createQuery.executeUpdate();
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateGuest(Guest guest) throws NotExistObject {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            session.saveOrUpdate(guest);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Guest> allGuests() {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query query = session.createQuery("from Guests g");

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<Guest>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Guest readGuest(int id) {
        Session session = null;
        try {
            session = HibernateConnector.getInstance().getSession();
            Query query = session.createQuery("from Guests g where g.idGuest = :id");
            query.setParameter("id", id);

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (Guest) queryList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Guest> last3Guests() {
        List<Guest> last3G = new ArrayList<>();
        List<Guest> guestList = allGuests();
        if (guestList.size() >= 3) {
            for (int i = guestList.size()-3; i < guestList.size(); i++) {
                last3G.add(guestList.get(i));
            }
        }
        return last3G;
    }

    @Override
    public void setGuests(List<Guest> guests) {

    }
}
