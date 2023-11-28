package io.github.nprcz;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Configuration
public class TestConfiguration {
    @Bean
    @Profile("integration")
    ProductRepository testProductRepo(){
        return new ProductRepository(){
            private Map<Integer, Product> products = new HashMap<>();
            @Override
            public List<Product> findAll() {
                return new ArrayList<>(products.values());
            }

            @Override
            public Page<Product> findAll(Pageable page) {
                return null;
            }

            @Override
            public List<Product> findByDone(boolean done) {
                return null;
            }

            @Override
            public boolean existsById(Integer id) {
                return products.containsKey(id);
            }

            @Override
            public Optional<Product> findById(Integer id) {
                return Optional.ofNullable(products.get(id));
            }

            @Override
            public Product save(Product entity) {
                return products.put(products.size()+1,entity);
            }

            @Override
            public boolean existsByDoneIsFalseAndOrder_Id(Integer orderId) {
                return false;
            }
        };
    }
}
