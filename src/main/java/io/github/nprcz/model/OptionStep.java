package io.github.nprcz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "option_steps")
public class OptionStep{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Option Step name: must not be empty")
    private String name;
    private int daysToDeadline;
    @ManyToOne
    @JoinColumn(name = "order_options_id")
    private OrderOptions orderOptions;


    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public OrderOptions getOrderOptions() {
        return orderOptions;
    }

    public void setOrderOptions(OrderOptions orderOptions) {
        this.orderOptions = orderOptions;
    }

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
}
