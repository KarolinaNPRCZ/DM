package io.github.nprcz.controller;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cglib.core.Local;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Test
    void httpGet_returnProductById() {
        //given
        Product productToSave = new Product("Papper", LocalDateTime.now());
        productRepository.save(productToSave);
        int id = productToSave.getId();
        //when
        Product result = restTemplate.getForObject("http://localhost:" + port + "/products/" + id, Product.class);
        //then
        assertThat(result).hasFieldOrPropertyWithValue("name", productToSave.getName());
    }

    @Test
    void httpPost_createProduct() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Product createProduct = new Product("Papper", LocalDateTime.now());
        HttpEntity<Product> httpEntity = new HttpEntity<>(createProduct, headers);
        //when
        Product result = restTemplate.postForObject("http://localhost:" + port + "/products", httpEntity, Product.class);
        //then
        assertThat(result).hasFieldOrPropertyWithValue("name", createProduct.getName());
    }

    @Test
    void httpPatch_shouldUpdateProduct() {
        //given
        Product productToUpdate = new Product("Product to Update", LocalDateTime.now());
        Product createProduct = productRepository.save(productToUpdate);
        String nameToUpdate = "Update";
        Product updateProduct = new Product(nameToUpdate, LocalDateTime.now());
        int id = createProduct.getId();


        //when
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/products/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(updateProduct),
                Void.class);
         //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Product fetchedProduct = productRepository.findById(id).orElse(null);
        assertThat(fetchedProduct).isNotNull();
        assertThat(fetchedProduct.getName()).isEqualTo(nameToUpdate);

    }

}