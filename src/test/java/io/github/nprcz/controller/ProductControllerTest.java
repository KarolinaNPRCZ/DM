package io.github.nprcz.controller;
import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//setup Random Port for this test
class ProductControllerE2ETest {
    //create variable with random port number injection by field !
    @LocalServerPort
    private int port;
    //symulation req under port like postman - allows asking other existing services like localhost or something
    @Autowired
    TestRestTemplate restTemplate;
    //for E2E test we need productRepository with SQLProductRepository
    @Autowired
    ProductRepository productRepository;
    @Test
    void httpGet_returnAllProducts() {
        //given
        //we need downloading size of all object in db because we have one flyaway migration which creating one object.
        //When we start our test with db in memory hibernate imposes migration
        int initial = productRepository.findAll().size();
        //save two products
        productRepository.save(new Product("Papper", LocalDateTime.now()));
        productRepository.save(new Product("Notebook", LocalDateTime.now()));
        //when
        //we send a query to a specific address to download a specific class
        Product[] result = restTemplate.getForObject("http://localhost:" + port + "/products", Product[].class);
        //then
        assertThat(result).hasSize(initial + 2);
    }
}