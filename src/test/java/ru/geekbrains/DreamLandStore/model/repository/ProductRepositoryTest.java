package ru.geekbrains.DreamLandStore.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/dropTest.sql", "/schemaTest.sql", "/dataTest.sql"})
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findAllByPriceBefore() {
        Product product = new Product(1L, "карандаш", new BigDecimal("16.00"));
        List<Product> productsExpected = new ArrayList<>();
        productsExpected.add(product);
        List<Product> allByPriceBeforeActual = productRepository.findAllByPriceBefore(new BigDecimal("16.50"));
        assertEquals(productsExpected,allByPriceBeforeActual);
    }

    @Test
    void findAllByPriceAfter() {
        Product product = new Product(5L, "резинка", new BigDecimal("25.00"));
        List<Product> productsExpected = new ArrayList<>();
        productsExpected.add(product);
        List<Product> allByPriceBeforeActual = productRepository.findAllByPriceAfter(new BigDecimal("20.50"));
        assertEquals(productsExpected,allByPriceBeforeActual);
    }

    @Test
    void findAllByPriceBetweenOrderByPrice() {
        Product product1 = new Product(3L, "тетрадь", new BigDecimal("18.00"));
        Product product2 = new Product(4L, "книга", new BigDecimal("20.00"));
        Product product3 = new Product(5L, "резинка", new BigDecimal("25.00"));
        List<Product> productsExpected = new ArrayList<>();
        productsExpected.add(product1);
        productsExpected.add(product2);
        productsExpected.add(product3);
        List<Product> allByPriceBeforeActual = productRepository.findAllByPriceBetweenOrderByPrice(new BigDecimal("17.50"),new BigDecimal("100"));
        assertEquals(productsExpected,allByPriceBeforeActual);
    }
}