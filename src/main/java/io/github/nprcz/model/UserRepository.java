package io.github.nprcz.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getById(int Id);
    Optional<User> getByEmail(String userEmail);
    User save(User entity);
    boolean existsById(Integer id);
    boolean existsByEmail(String userEmail);
    List<User> findAll();
    Page<User> findAll(Pageable page);

}
