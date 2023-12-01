package io.github.nprcz.controller;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("integration")
//when we run with this profile we are using productRepository in memory -
// we can say that is simulation when we asking all web layers and services but only repository was
// change because we dont want asking db
@AutoConfigureMockMvc//this adnotation set up testing without server
public class ProductControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;//this class allows in simple way making reqest and check for them assert
    @Autowired
    private ProductRepository productRepository;

    @Test
    void httpGet_returnGivenProductById() throws Exception {
        //given
        int id = productRepository.save(new Product("NoteBook A4", LocalDateTime.now())).getId();
        //when + then
        //mockMvc.perform(MockMvcRequestBuilders.get("/" + id)).andExpect(MockMvcResultMatchers.status());
        //static import ->
        //mockMvc.perform(get("/products/" + id)).andExpect(status().is2xxSuccessful());

        //reqest for /products/+ id addres -> instead giving all adress we give / because we woring on mock inmemory and we expect response status2xxSuc
        //query to the address "/products/" + id -> instead of providing the entire address in the uriTemplate, we provide / because we are working on mock inmemory
        //and we expect a stats response of 2xxSuccessful
        mockMvc.perform(get("/products/" + id)).andExpect(status().is2xxSuccessful());

    }
}
