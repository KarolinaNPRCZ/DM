package io.github.nprcz.controller;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

//soft integration test - unit test
@WebMvcTest(ProductController.class)
//specific clarification controller which we want use
public class ProductControllerLightIntegrationTest {
    @Autowired
    private MockMvc mockMvc;//this class allows in simple way making reqest and check for them assert
    @MockBean//adnotation with mockito - mocking bean - in this case we dont using integration profile
    private ProductRepository productRepository;

    @Test
    void httpGet_returnGivenProductById() throws Exception {
        //given
        //mocking repo when somth call this method return optional of
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(new Product("NoteBook A4", LocalDateTime.now())));
       //when + then
        //get to uri and print what you find and expect conten contains string
        mockMvc.perform(get("/products/1")).andDo(print())
                .andExpect(content().string(containsString("\"name\":\"NoteBook A4\"")));

    }
}
