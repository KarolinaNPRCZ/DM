package io.github.nprcz.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Page<Product> findAll(Pageable page);
    List<Product> findByDone(@Param("state") boolean done);
    boolean existsById(Integer id);
    Optional<Product> findById(Integer id);
    Product save(Product entity);
    boolean existsByDoneIsFalseAndOrder_Id(Integer orderId);
    List<Product> findAllByOrder_Id(Integer id);

    List<Product> readProductsToday(LocalDateTime date);
}
