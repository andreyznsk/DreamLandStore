package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.DreamLandStore.model.entry.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByOrderById();

    List<Product> findAllByOrderById(PageRequest pathSelectors);

    List<Product> findAllByPriceBefore(BigDecimal price);

    List<Product> findAllByPriceAfter(BigDecimal minPrice);

    List<Product> findAllByPriceBetweenOrderByPrice(BigDecimal minPrice, BigDecimal maxPrice);

}
