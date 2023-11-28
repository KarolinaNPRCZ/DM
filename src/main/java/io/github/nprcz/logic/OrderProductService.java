package io.github.nprcz.logic;

import io.github.nprcz.model.ProductOrder;
import io.github.nprcz.model.ProductOrderRepository;
import io.github.nprcz.model.ProductRepository;
import io.github.nprcz.model.projection.OrderReadModel;
import io.github.nprcz.model.projection.OrderWriteModel;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

//service is an intermediate layer between repo and controller.
//this is application service(we have yet domain service in DDD) this is very similiar to user story
//user want save new order with products

//@Service commented because we try using Bean conf in LogicConfiguration class
public class OrderProductService {
    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;

    public OrderProductService(final ProductOrderRepository productOrderRepository, final ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
    }

    public OrderReadModel createOrder(final OrderWriteModel source) {

        //if productOrderRepository.save(source.toProductOrder()) works we get database order
        ProductOrder result = productOrderRepository.save(source.toProductOrder());
        //from result we create orderReadModel
        return new OrderReadModel(result);

    }

    public List<OrderReadModel> readAll() {
        //findall -> stream and maping - for each download product order we creating new OrderReadModem and collect this to list
        return productOrderRepository.findAll().stream().map(OrderReadModel::new).collect(Collectors.toList());
        //mapping from the actual Order to the OrderReadModel so if someone modifies the list, nothing will happen,
        // changes to the ordermodel will not affect what is saved in the repo
        //we should make something like this for each entity
    }

    //we have an order that we want to close, but we cannot close the order if the order has
    // unclosed products (we use the product repository for this because there is a method that can check this)
    public void toggleOrder(int productOrderId) {
        //when we have productOrderId we can create productrepo.
        if (productRepository.existsByDoneIsFalseAndOrder_Id(productOrderId)) {
            //if something like this happens throw an exception, that order has unclosed product
            throw new IllegalStateException("Order has undone products. Done all the products first");
        }
        //if for this id we don't find product order, throw exception, in other way for result change value done to opposite
        ProductOrder result = productOrderRepository.findById(productOrderId)
                //We can created own exception witch inherits IllegalStateException
                .orElseThrow(() -> new IllegalStateException("ProductOrder with given id not found"));

        result.setDone(!result.isDone());
        productOrderRepository.save(result);
    }

}
