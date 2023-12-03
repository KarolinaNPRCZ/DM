package io.github.nprcz.controller;

import io.github.nprcz.logic.OrderProductService;
import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductRepository;
import io.github.nprcz.model.projection.OrderReadModel;
import io.github.nprcz.model.projection.OrderWriteModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderProductController {
    private final OrderProductService orderProductService;
    private final ProductRepository productRepository;
    public OrderProductController(OrderProductService orderProductService, ProductRepository productRepository) {
        this.orderProductService = orderProductService;
        this.productRepository = productRepository;
    }
    @PostMapping
     ResponseEntity<OrderReadModel> createOrder(@RequestBody @Valid OrderWriteModel source) {
        OrderReadModel result = orderProductService.createOrder(source);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @GetMapping
    public ResponseEntity<List<OrderReadModel>> readAll() {
        return ResponseEntity.ok(orderProductService.readAll());
    }
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleOrder(@PathVariable int id) {
        orderProductService.toggleOrder(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> readAllProductsFromOrder(@PathVariable int id) {
        return ResponseEntity.ok(productRepository.findAllByOrder_Id(id));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalAState(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
