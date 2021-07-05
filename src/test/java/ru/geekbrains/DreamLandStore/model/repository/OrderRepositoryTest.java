package ru.geekbrains.DreamLandStore.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.Order;
import ru.geekbrains.DreamLandStore.model.entry.OrderDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/dropTest.sql", "/schemaTest.sql", "/dataTest.sql"})
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void findAllByCustomerId() {
        List<Order> orderExpected = new ArrayList<>();
        Order nsk = new Order(1L, 1L, "Nsk", false,
                false, false, LocalDate.parse("2021-01-01"), BigDecimal.valueOf(10.1));
        orderExpected.add(nsk);
        List<Order> ordersActual = orderRepository.findAllByCustomerId(1L);
        ordersActual.get(0).setOrderDetailsList(new ArrayList<>());
        assertEquals(orderExpected,ordersActual);
    }

    @Test
    void saveTest() {
        List<Order> orderExpected = new ArrayList<>();
        Order nsk = new Order(1L, 1L, "Nsk", false,
                false, false, LocalDate.parse("2021-01-01"), BigDecimal.valueOf(10.1));
        Order newNsk = new Order(2L, 1L, "NskNew", false,
                false, false, LocalDate.parse("2021-01-01"), BigDecimal.valueOf(10.1));
        new ArrayList<OrderDetails>(List.of());
        orderExpected.add(nsk);
        orderExpected.add(newNsk);
        orderRepository.save(newNsk);
        List<Order> ordersActual = orderRepository.findAllByCustomerId(1L);
        ordersActual.get(0).setOrderDetailsList(new ArrayList<>());
        ordersActual.get(1).setOrderDetailsList(new ArrayList<>());
        assertEquals(orderExpected,ordersActual);
    }

    @Test
    void deleteByIdTest() {
        List<Order> orderExpected = new ArrayList<>();
        Order nsk = new Order(1L, 1L, "Nsk", false,
                false, false, LocalDate.parse("2021-01-01"), BigDecimal.valueOf(10.1));
        Order newNsk = new Order(2L, 1L, "NskNew", false,
                false, false, LocalDate.parse("2021-01-01"), BigDecimal.valueOf(10.1));
        orderExpected.add(nsk);
        orderRepository.save(newNsk);
        orderRepository.deleteById(2L);
        List<Order> ordersActual = this.orderRepository.findAllByCustomerId(1L);
        ordersActual.get(0).setOrderDetailsList(new ArrayList<>());
        assertEquals(orderExpected,ordersActual);
    }

    @Test
    void findAllByCustomerIdExceptionTest() {
        List<Order> orderExpected = new ArrayList<>();
        Order nsk = new Order(1L, 1L, "Nsk", false,
                false, false, LocalDate.parse("2021-01-01"), BigDecimal.valueOf(10.1));
        orderExpected.add(nsk);
        Optional<Order> byId = orderRepository.findById(100L);
        assertThrows(NullPointerException.class,
                ()->{ byId.orElseThrow(NullPointerException::new).getOrderId();
                });
    }


}