package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Country;
import org.example.domain.Passport;
import org.example.domain.Project;
import org.example.utils.DataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProjectCrudRepository implements CrudRepository<Project, Integer>{

    private static final Logger LOGGER = LogManager.getLogger(ProjectCrudRepository.class);

    private static DataSource dataSource;

    public ProjectCrudRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Project save(Project project) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.persist(project);
            transaction.commit();
            return project;
        } catch (Exception ex){
            if (transaction != null){
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
        return new Project();
    }

    @Override
    public List<Project> saveAll(List<Project> projects) {
        return projects.stream().map(this::save).toList();
    }

    @Override
    public Optional<Project> findById(Integer id) {
        try (Session session = dataSource.openSession()){
            Project project = session.get(Project.class, id);
            return Optional.of(project);
        } catch (Exception ex){
            LOGGER.error(ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Project> findAll() {
        final String SELECT_ALL_PROJECTS_JPQL = "from Project ";
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            List<Project> projects = session.createQuery(SELECT_ALL_PROJECTS_JPQL, Project.class).list();
            transaction.commit();
            return projects;
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
    public boolean updateId(Integer id, Project project) {
        Transaction transaction = null;
        final String UPDATE_PROJECT_BY_ID =  "update Project project " +
                "set project.projectName = :projectName " +
                "where project.id = :id";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            Query<Country> query = session.createQuery(UPDATE_PROJECT_BY_ID);
            query.setParameter("projectName", project.getProjectName());
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
        String DELETE_PROJECT_BY_ID_JPQL = "delete from Project where id = :id";
        try (Session session = dataSource.openSession()) {
            transaction = session.beginTransaction();

            Query<Country> query = session.createQuery(DELETE_PROJECT_BY_ID_JPQL);
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
    public void delete(Project project) {
        Transaction transaction = null;
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.remove(project);
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
        final String DELETE_ALL_PROJECT_JPQL= "delete from Project ";
        try (Session session = dataSource.openSession()){
            transaction = session.beginTransaction();
            session.createQuery(DELETE_ALL_PROJECT_JPQL).executeUpdate();
            transaction.commit();
        } catch (Exception ex){
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void deleteAll(List<Project> projects) {
        projects.forEach(this::delete);
    }
}
