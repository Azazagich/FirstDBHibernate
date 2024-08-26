package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Country;
import org.example.domain.User;
import org.example.utils.DataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserCrudRepository implements CrudRepository<User, Integer>{

    private static final Logger LOGGER = LogManager.getLogger(CountryCrudRepository.class);

    private static DataSource dataSource;

    public UserCrudRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    //TODO maybe
    @Override
    public User save(User user) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
        return new User();
    }

    @Override
    public List<User> saveAll(List<User> users) {
        return users.stream().map(this::save).toList();
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (Session session = dataSource.openSession()){
            User user = session.get(User.class, id);
            return Optional.of(user);
        } catch (Exception ex){
            LOGGER.error(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        final String SELECT_ALL_USERS_JPQL = "from User";
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            List<User> users = session.createQuery(SELECT_ALL_USERS_JPQL, User.class).list();
            transaction.commit();
            return users;
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
        return List.of();
    }

    @Override
    public boolean existById(Integer id) {
        return findById(id).isPresent();
    }

    //TODO
    @Override
    public boolean updateId(Integer id, User user) {
        Transaction transaction = null;
        final String UPDATE_USER_BY_ID = "update User user " +
                "set user.firstName = :firstName, user.email = :email, user.password = :password, " +
                "user.role = :role, user.country = :country, user.passport = :passport " +
                "where user.id = :id";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            Query<Country> query = session.createQuery(UPDATE_USER_BY_ID);
            query.setParameter("firstName", user.getFirstName());
            query.setParameter("email", user.getEmail());
            query.setParameter("password", user.getPassword());
            query.setParameter("role", user.getRole());
            query.setParameter("country", user.getCountry());
            query.setParameter("passport", user.getPassport());
            query.setParameter("id", user.getId());
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        Transaction transaction = null;
        String DELETE_USER_BY_ID_JPQL = "delete from User where id = :id";
        try (Session session = dataSource.openSession()) {
            transaction = session.beginTransaction();

            Query<Country> query = session.createQuery(DELETE_USER_BY_ID_JPQL);
            query.setParameter("id", id);
            query.executeUpdate();

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        Transaction transaction = null;
        final String DELETE_ALL_USERS_JPQL= "delete from User";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.createQuery(DELETE_ALL_USERS_JPQL).executeUpdate();
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void deleteAll(List<User> users) {
        users.forEach(this::delete);
    }
}
