package io.github.nprcz.model;

import jakarta.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "order_options_id")
    private OrderOptions orderOptions;
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

    public OrderOptions getOrderOptions() {
        return orderOptions;
    }

    public void setOrderOptions(OrderOptions orderOptions) {
        this.orderOptions = orderOptions;
    }

    public void updateFrom(ProductOrder source) {
        super.updateFrom(source);
        this.products = source.products;
    }
}
