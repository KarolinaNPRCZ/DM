package io.github.nprcz.adapter;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 interface SQLProductRepository extends ProductRepository, JpaRepository<Product,Integer > {
 @Override
 @Query(nativeQuery = true,value = "select count(*) > 0 from products where id=:id")
 boolean existsById(@Param("id")Integer id);
@Override
 boolean existsByDoneIsFalseAndOrder_Id(Integer orderId);
 @Override
 List<Product> findAllByOrder_Id(Integer id);

}
