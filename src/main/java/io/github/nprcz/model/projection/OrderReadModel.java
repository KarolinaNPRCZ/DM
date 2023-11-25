package io.github.nprcz.model.projection;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductOrder;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderReadModel {
    private String name;

    //Deadline from the latest product
    private LocalDateTime deadline;

    //what product in order
    private Set<OrderProductReadModel> products;

    //download order
    public OrderReadModel(ProductOrder source) {
        name = source.getName();
        //take date for each product and find maximal date - in all products;
        source.getProducts().stream().map(Product::getDeadline)
                .max(LocalDateTime::compareTo)
                .ifPresent(localDateTime -> deadline = localDateTime);//if this optional is present this will be maximum date
        // Using Stream map(Function mapper) to
        // collect the products in stream to
        //  new OrderProductReadModel
        //set our products to something what is in source for each create and collect new OrderProductReadModel
        products = source.getProducts()
                .stream().map(OrderProductReadModel::new)
                .collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<OrderProductReadModel> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductReadModel> products) {
        this.products = products;
    }
}
