package io.github.nprcz.controller;

import io.github.nprcz.logic.ProductService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(final ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }
    @GetMapping(params = {"!sort","!page","!size"})
    ResponseEntity<List<Product>> readAllProducts(){
        logger.warn("Exposing all the products!");
         //thenApply -> po tym jak obietnica sie wypełni -> mapuj na ResponseEntity ok
        return ResponseEntity.ok(productRepository.findAll());
    }
    @GetMapping
    ResponseEntity<List<Product>> readAllProducts(Pageable page){
        logger.info("Custom pagable");
        return ResponseEntity.ok(productRepository.findAll(page).getContent());
    }
    @GetMapping("/{id}")
    ResponseEntity<Product> getProductById(@PathVariable int id){
        logger.info("Finding product");
        return productRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
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

    @PostMapping
    ResponseEntity<Product> createProduct(@Valid @RequestBody  Product productToCreate){

        //  logger.info("Product Update");

        Product result =  productRepository.save(productToCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @Transactional
    @PatchMapping("/{id}")
   public ResponseEntity<?> toggleProduct(@PathVariable int id){
        if (!productRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        productRepository.findById(id).ifPresent(product -> product.setDone(!product.isDone()));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/done")
    ResponseEntity<List<Product>> getProductByDone(@RequestParam(defaultValue = "true") boolean state){
        logger.info("Finding product");
        return ResponseEntity.ok(productRepository.findByDone(state));
    }
    @GetMapping("/search/today")
    ResponseEntity<List<Product>> getProductsForToday(){
        logger.info("Finding product for Today");
        return ResponseEntity.ok(productRepository.readProductsToday(LocalDateTime.now()));
    }





}
