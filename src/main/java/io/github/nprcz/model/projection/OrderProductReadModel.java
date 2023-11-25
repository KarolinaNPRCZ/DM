package io.github.nprcz.model.projection;

import io.github.nprcz.model.Product;
//product read from order
public class OrderProductReadModel {
    // done for chceck
    private boolean done;
    //name to display
    private String name;

    public OrderProductReadModel(Product source) {
        name = source.getName();
        done = source.isDone();
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
