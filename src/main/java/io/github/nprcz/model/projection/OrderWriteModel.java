package io.github.nprcz.model.projection;

import io.github.nprcz.model.ProductOrder;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderWriteModel {
    private String name;
    private Set<OrderProductWriteModel> products;

    public ProductOrder toProductOrder(){
        var result = new ProductOrder();

        result.setName(name);//config name for order
        //we set result using mapping and for each product in order create product
        result.setProducts(
                products.stream()
                .map(source -> source.toProduct(result))
                .collect(Collectors.toSet()));
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OrderProductWriteModel> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductWriteModel> products) {
        this.products = products;
    }
}
