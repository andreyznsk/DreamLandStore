package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.DreamLandStore.model.entry.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomerId(Long customerId);
}
