package io.github.nprcz.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;



@Repository
 interface SQLProductRepository extends ProductRepository, JpaRepository<Product,Integer > {



}
