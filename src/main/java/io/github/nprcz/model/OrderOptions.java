package io.github.nprcz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "order_options")
public class OrderOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Option Order name : must not be empty")
    private String name;
    @OneToMany(mappedBy = "orderOptions")// zarządzanie taskami z poziomu grupy, pole zmapowane jako order
    private Set<ProductOrder> productsOrders;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderOptions")
    private Set<OptionStep> optionSteps;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProductOrder> getProductsOrders() {
        return productsOrders;
    }

    public void setProductsOrders(Set<ProductOrder> productsOrders) {
        this.productsOrders = productsOrders;
    }

    public Set<OptionStep> getOptionSteps() {
        return optionSteps;
    }

    public void setOptionSteps(Set<OptionStep> optionSteps) {
        this.optionSteps = optionSteps;
    }
}