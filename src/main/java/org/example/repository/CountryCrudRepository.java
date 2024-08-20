package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Country;
import org.example.utils.DataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;


public class CountryCrudRepository implements CrudRepository<Country, Integer> {

    private static final Logger LOGGER = LogManager.getLogger(CountryCrudRepository.class);

    private static DataSource dataSource;

    public CountryCrudRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Country save(Country country) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.persist(country);
            transaction.commit();
            return country;
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
        return new Country();
    }

    @Override
    public List<Country> saveAll(List<Country> countries) {
        return countries.stream().map(this::save).toList();
    }

    @Override
    public Optional<Country> findById(Integer id) {
        try (Session session = dataSource.openSession()){
            Country country = session.get(Country.class, id);
            return Optional.of(country);
        } catch (Exception ex){
            LOGGER.error(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Country> findAll() {
        final String SELECT_ALL_COUNTRIES_JPQL = "from Country";
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            List<Country> countries = session.createQuery(SELECT_ALL_COUNTRIES_JPQL, Country.class).list();
            transaction.commit();
            return countries;
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
    public boolean updateId(Integer id, Country country) {
        Transaction transaction = null;
        final String UPDATE_COUNTRY_BY_ID =  "update Country country " +
                "set country.name = :name, country.capital = :capital, country.currency = :currency " +
                "where country.id = :id";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            Query<Country> query = session.createQuery(UPDATE_COUNTRY_BY_ID);
            query.setParameter("name", country.getName());
            query.setParameter("capital", country.getCapital());
            query.setParameter("currency", country.getCurrency());
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
        String DELETE_COUNTRY_BY_ID_JPQL = "delete from Country where id = :id";
        try (Session session = dataSource.openSession()) {
            transaction = session.beginTransaction();

            Query<Country> query = session.createQuery(DELETE_COUNTRY_BY_ID_JPQL);
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
    public void delete(Country country) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.remove(country);
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
        final String DELETE_ALL_COUNTRIES_JPQL= "delete from Country";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.createQuery(DELETE_ALL_COUNTRIES_JPQL).executeUpdate();
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void deleteAll(List<Country> countries) {
        countries.forEach(this::delete);
    }
}
