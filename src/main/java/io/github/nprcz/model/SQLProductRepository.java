package io.github.nprcz.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
 interface SQLProductRepository extends ProductRepository, JpaRepository<Product,Integer > {
 @Override

 @Query(nativeQuery = true,value = "select count(*) > 0 from products where id=:id")
 boolean existsById(@Param("id")Integer id);
}
