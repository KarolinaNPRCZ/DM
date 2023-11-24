package io.github.nprcz.model;

import java.util.List;
import java.util.Optional;

public interface OrderOptionsRepository {
    List<OrderOptions> findAll();
    Optional<OrderOptions> findById(Integer id);
    OrderOptions save(OrderOptions entity);



}
