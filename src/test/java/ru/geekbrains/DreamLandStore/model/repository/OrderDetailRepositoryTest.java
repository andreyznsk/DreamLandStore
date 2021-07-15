package ru.geekbrains.DreamLandStore.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.OrderDetails;
import ru.geekbrains.DreamLandStore.model.entry.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/dropTest.sql", "/schemaTest.sql", "/dataTest.sql"})
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    void findAllByOrderId() {
        Product product = new Product(1L, "карандаш", BigDecimal.valueOf(16.0));
        OrderDetails orderDetailsExpected = new OrderDetails(1l, 1L, 1L, product);
        List<OrderDetails> allByOrderIdActual = orderDetailRepository.findAllByOrderId(1l);
        assertEquals(orderDetailsExpected,allByOrderIdActual.get(0));
    }
}