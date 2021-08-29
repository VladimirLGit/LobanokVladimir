package eu.senla.hotel.dao.hibernate;

import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.exception.NotExistObject;
import eu.senla.hotel.model.Guest;
import eu.senla.hotel.model.links.LinkGuest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Component
public class HGuestDao implements IGuestDao {
    @Override
    public boolean addGuest(Guest guest) {
        Transaction transaction;
        try (Session session = HibernateConnector.getInstance().getSession()) {
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
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Transaction beginTransaction = session.beginTransaction();
            Query createQuery = session.createQuery("delete from Guests guest where idGuest =:id");
            createQuery.setParameter("id", guest.getId());
            createQuery.executeUpdate();
            beginTransaction.commit();
            addGuestIntoHistory(guest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGuestIntoHistory(Guest guest) {
        Transaction transaction;
        LinkGuest linkGuest = new LinkGuest(guest.getId(),
                guest.getName(),
                guest.getDateOfCheckIn(),
                guest.getDateOfCheckOut(),
                guest.getState());
        try (Session session = HibernateConnector.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.save(linkGuest);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGuest(Guest guest) throws NotExistObject {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Transaction beginTransaction = session.beginTransaction();
            session.saveOrUpdate(guest);
            session.flush();
            beginTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Guest> allGuests() {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Query query = session.createQuery("from Guests");

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                return (List<Guest>) queryList;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Guest readGuest(int id) {
        try (Session session = HibernateConnector.getInstance().getSession()) {
            Query query = session.createQuery("from Guests g where g.idGuest = :id");
            query.setParameter("id", id);

            List queryList = query.list();
            if (queryList != null && queryList.isEmpty()) {
                return null;
            } else {
                assert queryList != null;
                return (Guest) queryList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
