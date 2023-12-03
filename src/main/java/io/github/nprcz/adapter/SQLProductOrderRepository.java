package io.github.nprcz.adapter;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductOrder;
import io.github.nprcz.model.ProductOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
 interface SQLProductOrderRepository extends ProductOrderRepository, JpaRepository<ProductOrder,Integer > {
 @Override
 @Query("select distinct o from ProductOrder o join fetch o.products")
 List<ProductOrder> findAll();
 @Override
 boolean existsByDoneIsFalseAndOrderOptions_Id(Integer orderOptionsId);

}
