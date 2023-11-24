package io.github.nprcz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "product_orders")
public class ProductOrder extends BaseProduct{
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Product order's name: must not be empty")
    private String name;
    private boolean done;*/
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")// zarządzanie taskami z poziomu grupy, pole zmapowane jako order
    private Set<Product> products;
    @Embedded
    private Audit audit = new Audit();
    public ProductOrder() {
    }

    public Set<Product> getProducts() {
        return products;
    }

     void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void updateFrom(ProductOrder source) {
        super.updateFrom(source);
        this.products = source.products;
    }
}
