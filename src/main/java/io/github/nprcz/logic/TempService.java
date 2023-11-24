package io.github.nprcz.logic;

import io.github.nprcz.model.Product;
import io.github.nprcz.model.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TempService {
@Autowired
List<String> temp(ProductOrderRepository productOrderRepository){
  return  productOrderRepository.findAll().stream()
          .flatMap(productOrder -> productOrder.getProducts().stream())
          .map(Product::getName)
          .collect(Collectors.toList());

}
}
