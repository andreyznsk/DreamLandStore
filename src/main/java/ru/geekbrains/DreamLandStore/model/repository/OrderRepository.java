package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.DreamLandStore.model.entry.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomerId(Long customerId);

    @Override
    Optional<Order> findById(Long aLong);

    }
