package io.github.nprcz;

import com.zaxxer.hikari.util.DriverDataSource;
import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductOrder;
import io.github.nprcz.model.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.*;

@Configuration
public class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")//non integration profile

    //configuration only for datasource our h2 db for tests
    DataSource e2eTestDataSource(){
        //we need connecting jdbc protocole with db in memory
        //we must set flag because hibernate imposes migration, which need check existing tables
        //when we aren't set flag. Connect are closing after closing connection with db and this is to fast for us.
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1","","");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
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
                int key = products.size()+1;
                try {
                    //complement id field in entity in memory using reflection
                    var field  =  Product.class.getSuperclass().getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                 products.put(key,entity);
                return products.get(key);
            }

            @Override
            public boolean existsByDoneIsFalseAndOrder_Id(Integer orderId) {
                return false;
            }
        };
    }
}
