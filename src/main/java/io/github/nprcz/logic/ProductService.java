package io.github.nprcz.logic;

import io.github.nprcz.controller.LoggerFilter;
import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//class that will run the thread and query the repo in the background
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CompletableFuture<List<Product>> findAllAsync(){
        //factoring asynchronously.
        // We will post our repo in a separate thread.
        // The overstretched method can be set on which executor to run - not on the main pool
        logger.info("Async find!");
        return CompletableFuture.supplyAsync(productRepository::findAll);
    }
}
