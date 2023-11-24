package io.github.nprcz.adapter;

import io.github.nprcz.model.OrderOptions;
import io.github.nprcz.model.OrderOptionsRepository;
import io.github.nprcz.model.ProductOrder;
import io.github.nprcz.model.ProductOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 interface SQLOrderOptionsRepository extends OrderOptionsRepository, JpaRepository<OrderOptions,Integer > {
 @Override
 @Query("select distinct o from OrderOptions o join fetch o.optionSteps")
 List<OrderOptions> findAll();

}
