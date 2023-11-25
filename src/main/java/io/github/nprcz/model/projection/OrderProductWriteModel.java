package io.github.nprcz.model.projection;

import io.github.nprcz.model.Product;

import java.time.LocalDateTime;
//this is DTO which we using for to transfer data with safty way becouse we aren't shering more that we need
//Product from order
public class OrderProductWriteModel {
    private String name;
    private LocalDateTime deadline;

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
    //product must be created from order
    public Product toProduct(){
       return new Product(name,deadline);

    }
}
