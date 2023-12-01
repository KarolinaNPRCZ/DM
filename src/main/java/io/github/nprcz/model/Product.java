package io.github.nprcz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product extends BaseProduct {
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Products name: must not be empty")
    private String name;*/
    @NotNull(message = "Products quantity must be not null")
    @Min(value=0, message="Products quantity: positive number, the value must be a minimum of 0")
    private int quantity;


    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();
    @ManyToOne
    @JoinColumn(name = "product_order_id")
    private ProductOrder order;



    public Product() {
    }
    public Product(String name, LocalDateTime deadline) {
        super(name);
        this.deadline = deadline;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public LocalDateTime getDeadline() {
        return deadline;
    }

    ProductOrder getOrder() {
        return order;
    }

  public   void setOrder(ProductOrder order) {
        this.order = order;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void updateFrom(final Product source){
        super.updateFrom(source);
        quantity = source.quantity;
       deadline = source.deadline;
       order =source.order;
    }
}
