package ru.geekbrains.DreamLandStore.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.DreamLandStore.model.entry.Role;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/drop.sql","/schema.sql","/data.sql"})
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    void findOneByName() {
        Role role = new Role(3L, "ROLE_CLIENT",new ArrayList<>());
        List<Role> role_clientExpected = new ArrayList<>();
        role_clientExpected.add(role);
        List<Role> role_clientActual = roleRepository.findOneByName("ROLE_CLIENT");
        assertEquals(role_clientExpected,role_clientActual);
    }
}