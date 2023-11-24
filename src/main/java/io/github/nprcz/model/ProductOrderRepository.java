package io.github.nprcz.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOrderRepository {
   List<ProductOrder> findAll();
   Optional<ProductOrder> findById(Integer id);
   ProductOrder save(ProductOrder entity);



}
