package ru.geekbrains.DreamLandStore.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.entry.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/drop.sql","/schema.sql","/data.sql"})
class ChartRepositoryTest {

    @Autowired
    private ChartRepository chartRepository;

    @Test
    void findAll() {
        Product product = new Product(1L, "карандаш", new BigDecimal("16.00"));
        Product product1 = new Product(2L, "карандаш2", new BigDecimal("17.00"));
        List<Chart> chartsExpected = new ArrayList<>();
        chartsExpected.add(new Chart(1L,1L,1L,product));
        chartsExpected.add(new Chart(2L,1L,2L,product1));
        List<Chart> chartsActual = chartRepository.findAll();
        assertEquals(chartsExpected, chartsActual);

    }

    @Test
    void findAllByCustomerId() {
        Product product = new Product(1L, "карандаш", new BigDecimal("16.00"));
        Product product1 = new Product(2L, "карандаш2", new BigDecimal("17.00"));
        List<Chart> chartsExpected = new ArrayList<>();
        chartsExpected.add(new Chart(1L,1L,1L,product));
        chartsExpected.add(new Chart(2L,1L,2L,product1));
        List<Chart> chartsActual = chartRepository.findAllByCustomerId(1L);
        assertEquals(chartsExpected, chartsActual);
    }

    @Test
    void findAllByCustomerIdNull() {
        List<Chart> chartsActual = chartRepository.findAllByCustomerId(3L);
        assertEquals(new ArrayList<>(), chartsActual);
    }


    @Test
    void deleteAllByCustomerId() {
        chartRepository.deleteAllByCustomerId(1L);
        List<Chart> chartsActual = chartRepository.findAllByCustomerId(1L);
        assertEquals(new ArrayList<>(), chartsActual);
    }
}