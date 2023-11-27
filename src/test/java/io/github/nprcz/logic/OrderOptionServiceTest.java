package io.github.nprcz.logic;

import io.github.nprcz.ProductConfigurationProperties;
import io.github.nprcz.model.*;
import io.github.nprcz.model.projection.OrderProductReadModel;
import io.github.nprcz.model.projection.OrderReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.util.Assert.isInstanceOf;

class OrderOptionServiceTest {

    @Test
// if (!configurationProperties.getTemplate().isAllowMultipleProducts() && orderRepository.existsByDoneIsFalseAndOrderOptions_Id(orderOptionId)){
    @DisplayName("Should throw IllegalStateException when configured to allow just 1 Product Order and undone Product Order exists")
    void createProductOrder_noMultipleProductsConfig_And_undoneProductOrderExists_throwsIllegalStateException() {

        var mockProductOrderRepository = productOrderRepositoryReturning(true);

        var toTest = new OrderOptionService(null, mockProductOrderRepository, configurationPropertiesRunning(false));

        var exception = catchThrowable(() -> toTest.createGroup(9, LocalDateTime.now()));

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Product Order for Options");


    }

    @Test
    @DisplayName("Should throw IllegalStateException when configurstion ok and no OrderOptions exists for Id")
    void createProductOrder_configOk_And_noOrderOptionsById_throwsIllegalStateException() {

        var mockConfig = configurationPropertiesRunning(true);

        var mockOrderOptionRepository = mock(OrderOptionsRepository.class);
        when(mockOrderOptionRepository.findById(anyInt())).thenReturn(Optional.empty());

        var toTest = new OrderOptionService(mockOrderOptionRepository, null, mockConfig);

        var exception = catchThrowable(() -> toTest.createGroup(9, LocalDateTime.now()));

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("given id not found");


    }

    @Test
    @DisplayName("Should throw IllegalStateException when configurationto allow just 1 Product Order and undone Product Order not exists")
    void createProductOrder_configForOnlyOne_And_undoneProductOrderNotExists_throwsIllegalStateException() {
        var mockProductOrderRepository = productOrderRepositoryReturning(false);
        var mockConfig = configurationPropertiesRunning(true);
        var mockOrderOptionRepository = mock(OrderOptionsRepository.class);
        when(mockOrderOptionRepository.findById(anyInt())).thenReturn(Optional.empty());


        var toTest = new OrderOptionService(mockOrderOptionRepository, mockProductOrderRepository, mockConfig);

        var exception = catchThrowable(() -> toTest.createGroup(9, LocalDateTime.now()));

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Options with given id");


    }

    @Test
    @DisplayName("Should create, a new ProductOrder from OrderOptions")
    void createProductOrder_configOk_existingOrderOptions() {
        //given
        var today = LocalDate.now().atStartOfDay(); // data dzisiejsza z 0 czasem
        //and
        var orderOption = orderOptionsWith("opis",Set.of(-1,-2));
        var mockOrderOptionRepository = mock(OrderOptionsRepository.class);
        when(mockOrderOptionRepository.findById(anyInt()))
                .thenReturn(Optional.of(orderOption));
        // and
        InMemoryProductOrderRepo inMemoryProductOrderRepository =  inMemoryProductOrderRepo();
        int countBeforeCall = inMemoryProductOrderRepository.count();
        // and
        ProductConfigurationProperties mockConfig = configurationPropertiesRunning(true);


        //system under test
        var toTest = new OrderOptionService(mockOrderOptionRepository, inMemoryProductOrderRepository, mockConfig);
        //when
        OrderReadModel result = toTest.createGroup(1,today);
        //then
        assertThat(result.getName()).isEqualTo("opis");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getProducts()).allMatch(product -> product.getName().equals("foo"));
        assertThat(countBeforeCall+1).isEqualTo(inMemoryProductOrderRepository.count());



    }

    private OrderOptions orderOptionsWith(String orderOptionName,Set<Integer> daysToDeadline){

        Set<OptionStep> steps = daysToDeadline.stream()
                .map(days -> {
                    var step = mock(OptionStep.class);
                    when(step.getName()).thenReturn("foo");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(OrderOptions.class);
        when(result.getName()).thenReturn(orderOptionName);
        when(result.getOptionSteps()).thenReturn(steps);
  return result;
    }
    private static ProductOrderRepository productOrderRepositoryReturning(boolean value) {
        var mockProductOrderRepository = mock(ProductOrderRepository.class);
        when(mockProductOrderRepository.existsByDoneIsFalseAndOrderOptions_Id(anyInt())).thenReturn(value);
        return mockProductOrderRepository;
    }

    private static ProductConfigurationProperties configurationPropertiesRunning(boolean result) {
        var mockTemplate = mock(ProductConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleProducts()).thenReturn(result);

        var mockConfig = mock(ProductConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }
   private InMemoryProductOrderRepo inMemoryProductOrderRepo() { return new InMemoryProductOrderRepo();}
   private static class InMemoryProductOrderRepo implements ProductOrderRepository{
               private int index = 0;
               private Map<Integer, ProductOrder> map = new HashMap<>();
               public int count(){
                   return map.values().size();
               }

               @Override
               public List<ProductOrder> findAll() {
                   return new ArrayList<>(map.values());
               }

               @Override
               public Optional<ProductOrder> findById(Integer id) {
                   return Optional.ofNullable(map.get(id));
               }

               @Override
               public ProductOrder save(ProductOrder entity) {
                   if (entity.getId() == 0) {
                       try {
                        var field  =  ProductOrder.class.getSuperclass().getDeclaredField("id");
                        field.setAccessible(true);
                        field.set(entity, ++index);
                       } catch (NoSuchFieldException | IllegalAccessException e) {
                           throw new RuntimeException(e);
                       }

                   }
                   map.put(entity.getId(), entity);
                   return entity;
               }

               @Override
               public boolean existsByDoneIsFalseAndOrderOptions_Id(final Integer orderOptionsId) {
                   return map.values().stream().filter(productOrder -> !productOrder.isDone())
                           .anyMatch(productOrder -> productOrder.getOrderOptions() != null && productOrder.getOrderOptions()
                                   .getId() == orderOptionsId);
               }
           };

       }//repo trzymane w pamięci
       // jak mamy jedna metode to używamy mockito ale jeżeli chcemy sprawdzić czy cos istnieje po utworzeniu
       // to tak sobie zrobilismy xd


