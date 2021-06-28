package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.DreamLandStore.model.entry.Chart;

import java.util.List;

@Repository
public interface ChartRepository extends JpaRepository<Chart,Long> {
    @Override
    List<Chart> findAll();

    List<Chart> findAllByCustomerId(Long id);

    @Transactional
    void deleteAllByCustomerId(long customerId);

    void delete(Chart chart);
}
