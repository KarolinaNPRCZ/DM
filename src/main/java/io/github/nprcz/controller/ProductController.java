package io.github.nprcz.controller;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping(value = "/products",params = {"!sort","!page","!size"})
    ResponseEntity<List<Product>> readAllProducts(){
        logger.warn("Exposing all the products!");
        return ResponseEntity.ok(productRepository.findAll());
    }
    @GetMapping("/products")
    ResponseEntity<List<Product>> readAllProducts(Pageable page){
        logger.info("Custom pagable");
        return ResponseEntity.ok(productRepository.findAll(page).getContent());
    }
    @GetMapping("/products/{id}")
    ResponseEntity<Product> getProductById(@PathVariable int id){
        logger.info("Finding product");
        return productRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/products/{id}")
    ResponseEntity<?> updateProductById(@PathVariable int id, @RequestBody @Valid Product productToUpdate){
        if (!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productRepository.findById(id).ifPresent(product -> {
            product.updateFrom(productToUpdate);
            productRepository.save(product);
        });
        return ResponseEntity.noContent().build();
    }
   /* @PutMapping("/products/{id}")
    ResponseEntity<?> updateProductById(@PathVariable int id, @RequestBody @Valid Product productToUpdate){
        if (!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productRepository.findById(id).ifPresent(product -> product.updateFrom(productToUpdate));
        {
            product.updateFrom(productToUpdate);
            productRepository.save(product);
        });
        return ResponseEntity.noContent().build();
    }*/
    @PostMapping("/products")
    ResponseEntity<Product> createProduct(@Valid @RequestBody  Product productToCreate){

        //  logger.info("Product Update");

        Product result =  productRepository.save(productToCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @Transactional
    @PatchMapping("/products/{id}")
   public ResponseEntity<?> toggleProduct(@PathVariable int id){
        if (!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productRepository.findById(id).ifPresent(product -> product.setDone(!product.isDone()));
        return ResponseEntity.noContent().build();
    }





}
