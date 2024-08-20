package org.example.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CrudRepository<E, I extends Integer> {
    E save(E entity);

    List<E> saveAll(List<E> entities);

    Optional<E> findById(I id);

    List<E> findAll();

    boolean existById(I id);

    boolean updateId(I id, E entity);

    void deleteById(I id);

    void delete(E entity);

    void deleteAll();

    void deleteAll(List<E> entities);
}
