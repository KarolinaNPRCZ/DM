package io.github.nprcz.logic;

import io.github.nprcz.ProductConfigurationProperties;
import io.github.nprcz.model.OrderOptionsRepository;
import io.github.nprcz.model.ProductOrderRepository;
import io.github.nprcz.model.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {
    @Bean
    OrderProductService OrderProductService(final ProductOrderRepository productOrderRepository,
                                            final ProductRepository productRepository) {

        return new OrderProductService(productOrderRepository,productRepository);
    }

    @Bean
    OrderOptionService OrderOptionService(final OrderOptionsRepository optionsRepository,
                                            final ProductOrderRepository orderRepository, final ProductConfigurationProperties productConfigurationProperties,final OrderProductService orderProductService) {

        return new OrderOptionService(optionsRepository,orderRepository,productConfigurationProperties, orderProductService);
    }


}
