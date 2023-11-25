package io.github.nprcz.logic;

import io.github.nprcz.ProductConfigurationProperties;
import io.github.nprcz.model.*;
import io.github.nprcz.model.projection.OrderReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderOptionService {
    private OrderOptionsRepository optionsRepository;
    private ProductOrderRepository orderRepository;
    private ProductConfigurationProperties configurationProperties;

    public OrderOptionService(OrderOptionsRepository optionsRepository, ProductOrderRepository orderRepository, ProductConfigurationProperties configurationProperties) {
        this.optionsRepository = optionsRepository;
        this.orderRepository = orderRepository;
        this.configurationProperties = configurationProperties;
    }

    public OrderOptions createOrder(final OrderOptions source) {
        return optionsRepository.save(source);
    }

    public List<OrderOptions> readAll() {
        return optionsRepository.findAll();
    }
    //logikę tworzenia grupy
    public OrderReadModel createGroup(int orderOptionId, LocalDateTime deadline) {
        if (!configurationProperties.getTemplate().isAllowMultipleProducts() && orderRepository.existsByDoneIsFalseAndOrderOptions_Id(orderOptionId)){
            throw new IllegalStateException("Only one undone Product Order for Options is allowed");

        }

        ProductOrder result = optionsRepository.findById(orderOptionId)
                .map(orderOptions -> {
            var targetOrder = new ProductOrder();
            targetOrder.setName(orderOptions.getName());
            targetOrder.setProducts(orderOptions.getOptionSteps().stream()
                    //wyliczać deadline'y zadań na postawie kroków projektu i podanego deadline'u
                    .map(optionStep -> new Product(optionStep.getName(),deadline.plusDays(optionStep.getDaysToDeadline())))
                    .collect(Collectors.toSet()));
                    return targetOrder;
        }).orElseThrow(()-> new IllegalStateException("Order Options with given id not found"));
        return new OrderReadModel(result);
    }


}
