package io.github.nprcz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class BaseProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Products name: must not be empty")
    private String name;
    private boolean done;
    /*@Embedded
    private Audit audit = new Audit();*/

    public BaseProduct(String name) {
        this.name = name;
    }

    public BaseProduct() {
    }

    public void updateFrom(final BaseProduct source){
        name = source.name;
        done = source.done;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
