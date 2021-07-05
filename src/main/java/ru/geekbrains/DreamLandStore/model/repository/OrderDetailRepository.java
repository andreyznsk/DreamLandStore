package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.DreamLandStore.model.entry.OrderDetails;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetails,Long> {

    List<OrderDetails> findAllByOrderId(Long id);
}
