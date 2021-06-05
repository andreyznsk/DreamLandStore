package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.DreamLandStore.model.entry.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
   List<Role> findOneByName(String role_client);
}
