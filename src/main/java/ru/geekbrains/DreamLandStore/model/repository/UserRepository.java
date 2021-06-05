package ru.geekbrains.DreamLandStore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;

public interface UserRepository extends JpaRepository<MyUser,Long> {

    MyUser findOneByUsername(String username);
}
