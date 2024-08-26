package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Country;
import org.example.domain.Passport;
import org.example.utils.DataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PassportCrudRepository implements CrudRepository<Passport, Integer>{

    private static final Logger LOGGER = LogManager.getLogger(PassportCrudRepository.class);

    private static DataSource dataSource;

    public PassportCrudRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Passport save(Passport passport) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.persist(passport);
            transaction.commit();
            return passport;
        } catch (Exception ex){
            if (transaction != null){
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
        return new Passport();
    }

    @Override
    public List<Passport> saveAll(List<Passport> passports) {
        return passports.stream().map(this::save).toList();
    }

    @Override
    public Optional<Passport> findById(Integer id) {
        try (Session session = dataSource.openSession()){
            Passport passport = session.get(Passport.class, id);
            return Optional.of(passport);
        } catch (Exception ex){
            LOGGER.error(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Passport> findAll() {
        final String SELECT_ALL_PASSPORTS_JPQL = "from Passport ";
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            List<Passport> passports = session.createQuery(SELECT_ALL_PASSPORTS_JPQL, Passport.class).list();
            transaction.commit();
            return passports;
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

    @Override
    public boolean updateId(Integer id, Passport passport) {
        Transaction transaction = null;
        final String UPDATE_PASSPORT_BY_ID =  "update Passport passport " +
                "set passport.fullName = :fullName, passport.dateOfBirth = :dateOfBirth, " +
                "passport.hometown = :hometown, passport.sex = :sex, passport.passportCode = :passportCode " +
                "where passport.id = :id";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            Query<Country> query = session.createQuery(UPDATE_PASSPORT_BY_ID);
            query.setParameter("fullName", passport.getFullName());
            query.setParameter("dateOfBirth", passport.getDateOfBirth());
            query.setParameter("hometown", passport.getHometown());
            query.setParameter("sex", passport.getSex());
            query.setParameter("passportCode", passport.getPassportCode());
            query.setParameter("id", id).executeUpdate();
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
        String DELETE_PASSPORT_BY_ID_JPQL = "delete from Passport where id = :id";
        try (Session session = dataSource.openSession()) {
            transaction = session.beginTransaction();

            Query<Country> query = session.createQuery(DELETE_PASSPORT_BY_ID_JPQL);
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
    public void delete(Passport passport) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.remove(passport);
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
        final String DELETE_ALL_PASSPORT_JPQL= "delete from Passport";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.createQuery(DELETE_ALL_PASSPORT_JPQL).executeUpdate();
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void deleteAll(List<Passport> passports) {
        passports.forEach(this::delete);
    }
}
