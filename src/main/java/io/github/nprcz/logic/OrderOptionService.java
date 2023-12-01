package io.github.nprcz.logic;

import io.github.nprcz.ProductConfigurationProperties;
import io.github.nprcz.model.*;
import io.github.nprcz.model.projection.OrderProductWriteModel;
import io.github.nprcz.model.projection.OrderReadModel;
import io.github.nprcz.model.projection.OrderWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class OrderOptionService {
    private OrderOptionsRepository optionsRepository;
    private ProductOrderRepository orderRepository;
    private ProductConfigurationProperties configurationProperties;
    private OrderProductService orderProductService;

    public OrderOptionService(OrderOptionsRepository optionsRepository, ProductOrderRepository orderRepository, ProductConfigurationProperties configurationProperties, OrderProductService orderProductService) {
        this.optionsRepository = optionsRepository;
        this.orderRepository = orderRepository;
        this.configurationProperties = configurationProperties;

        this.orderProductService = orderProductService;
    }

    public OrderOptions createOrder(final OrderOptions source) {
        return optionsRepository.save(source);
    }

    public List<OrderOptions> readAll() {
        return optionsRepository.findAll();
    }

    //logikę tworzenia grupy
    public OrderReadModel createGroup(int orderOptionId, LocalDateTime deadline) {
        if (!configurationProperties.getTemplate().isAllowMultipleProducts() && orderRepository.existsByDoneIsFalseAndOrderOptions_Id(orderOptionId)) {
            throw new IllegalStateException("Only one undone Product Order for Options is allowed");

        }

        OrderReadModel result = optionsRepository.findById(orderOptionId)
                .map(orderOptions -> {
                    var targetOrder = new OrderWriteModel();
                    targetOrder.setName(orderOptions.getName());
                    targetOrder.setProducts(
                            orderOptions.getOptionSteps().stream()
                                    .map(optionStep -> {
                                        var product = new OrderProductWriteModel();
                                        product.setName(optionStep.getName());
                                        product.setDeadline(deadline.plusDays(optionStep.getDaysToDeadline()));
                                        return product;
                                    }).collect(Collectors.toSet()));
                    return orderProductService.createOrder(targetOrder);

                }).orElseThrow(() -> new IllegalStateException("Order Options with given id not found"));
        return result;
    }


}
