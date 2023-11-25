package io.github.nprcz.logic;

import io.github.nprcz.ProductConfigurationProperties;
import io.github.nprcz.model.OrderOptionsRepository;
import io.github.nprcz.model.ProductOrder;
import io.github.nprcz.model.ProductOrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.isInstanceOf;

class OrderOptionServiceTest {

    @Test// if (!configurationProperties.getTemplate().isAllowMultipleProducts() && orderRepository.existsByDoneIsFalseAndOrderOptions_Id(orderOptionId)){
    @DisplayName("Should throw IllegalStateException when configured to allow just 1 Product Order and undone Product Order exists")
     void createProductOrder_noMultipleProductsConfig_And_undoneProductOrderExists_throwsIllegalStateException() {

        var mockProductOrderRepository = productOrderRepositoryReturning(true);

        var toTest = new OrderOptionService(null, mockProductOrderRepository, ConfigurationPropertiesRunning(false));

        var exception = catchThrowable(() -> toTest.createGroup(9, LocalDateTime.now()));

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Product Order for Options");



    }

    @Test
    @DisplayName("Should throw IllegalStateException when configurstion ok and no OrderOptions exists for Id")
    void createProductOrder_configOk_And_noOrderOptionsById_throwsIllegalStateException() {

        var mockConfig = ConfigurationPropertiesRunning(true);

        var mockOrderOptionRepository = mock(OrderOptionsRepository.class);
        when(mockOrderOptionRepository.findById(anyInt())).thenReturn(Optional.empty());

        var toTest = new OrderOptionService(mockOrderOptionRepository, null, mockConfig);

        var exception = catchThrowable(() -> toTest.createGroup(9, LocalDateTime.now()));

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("given id not found");



    }
    @Test
    @DisplayName("Should throw IllegalStateException when configuration say we have only one and undone Product Order doesn't exists")
    void createProductOrder_configForOnlyOne_And_undoneProductOrderNotExists_throwsIllegalStateException() {
        var mockProductOrderRepository = productOrderRepositoryReturning(false);
        var mockConfig = ConfigurationPropertiesRunning(true);
        var mockOrderOptionRepository = mock(OrderOptionsRepository.class);
        when(mockOrderOptionRepository.findById(anyInt())).thenReturn(Optional.empty());


        var toTest = new OrderOptionService(mockOrderOptionRepository, mockProductOrderRepository, mockConfig);

        var exception = catchThrowable(() -> toTest.createGroup(9, LocalDateTime.now()));

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Options with given id");



    }

    private static ProductOrderRepository productOrderRepositoryReturning(boolean value) {
        var mockProductOrderRepository = mock(ProductOrderRepository.class);
        when(mockProductOrderRepository.existsByDoneIsFalseAndOrderOptions_Id(anyInt())).thenReturn(value);
        return mockProductOrderRepository;
    }

    private static ProductConfigurationProperties ConfigurationPropertiesRunning(boolean result) {
        var mockTemplate = mock(ProductConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleProducts()).thenReturn(result);

        var mockConfig = mock(ProductConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    @Test
    void createGroup() {
        //zwrtacanie wyjątku


    }
}