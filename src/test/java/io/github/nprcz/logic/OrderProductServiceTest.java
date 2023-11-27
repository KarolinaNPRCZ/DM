package io.github.nprcz.logic;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductOrder;
import io.github.nprcz.model.ProductOrderRepository;
import io.github.nprcz.model.ProductRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderProductServiceTest {
    @Test
    void toggleOrder_undoneProduct_throwsIllegalStateException() {
        //given

        var mockProductRepository = getProductRepositoryRunning(true);
        var mockProductOrderRepository = mock(ProductOrderRepository.class);
    //   when
        OrderProductService result = new OrderProductService(mockProductOrderRepository,mockProductRepository);

        //when

        var exception = catchThrowable(() -> result.toggleOrder(anyInt()));

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Order has undone products");


    }

    private static ProductRepository getProductRepositoryRunning(boolean value) {
        var mockProductRepository = mock(ProductRepository.class);
        when(mockProductRepository.existsByDoneIsFalseAndOrder_Id(anyInt())).thenReturn(value);
        return mockProductRepository;
    }

    @Test
    void toggleOrder_noMultipleProdConfTrue_And_noProductOrderByID_throwsIllegalStateException() {
        //given

        var mockProductRepository = getProductRepositoryRunning(false);
        var mockProductOrderRepository = mock(ProductOrderRepository.class);
        when(mockProductOrderRepository.findById(anyInt())).thenReturn(Optional.empty());
        OrderProductService result = new OrderProductService(mockProductOrderRepository,mockProductRepository);

        //when

        var exception = catchThrowable(() -> result.toggleOrder(anyInt()));

        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("given id not");


    }
    @Test
    void toggleOrder_noMultipleProdConfTrue_And_ProductOrderByIdExists_DoneStateChange() {
        //given
        ProductOrder productOrder = new ProductOrder();
        var mockProductRepository = getProductRepositoryRunning(false);
        var mockProductOrderRepository = mock(ProductOrderRepository.class);
        when(mockProductOrderRepository.findById(anyInt())).thenReturn(Optional.of(productOrder));
        OrderProductService result = new OrderProductService(mockProductOrderRepository,mockProductRepository);
        boolean doneBefore = productOrder.isDone();
        //when

        //var exception = catchThrowable(() -> result.toggleOrder(anyInt()));
        result.toggleOrder(anyInt());

        //then
       // assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("given id not");
        assertThat(doneBefore).isNotEqualTo(productOrder.isDone());


    }


}