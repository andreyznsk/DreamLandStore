package ru.geekbrains.DreamLandStore.model.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.Role;
import ru.geekbrains.DreamLandStore.model.repository.RoleRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/drop.sql","/schema.sql","/data.sql"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserNameTest() {
        MyUser myUserExpected = new MyUser(2L,"andrey","$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.",
                "Andrey", "Zaitsev", "2@2.ru",new ArrayList<>());
        MyUser andreyActual = userRepository.findOneByUsername("andrey");
        assertEquals(myUserExpected, andreyActual);
    }

    @Test
    void findByUserNameTestNull() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        MyUser andreyActual = userRepository.findOneByUsername(generatedString);
        assertNull(andreyActual);
    }

    @Test
    void saveTest() {
        MyUser myUserExpected = new MyUser(3L,"newUser","testPassword",
                "Andrey", "Zaitsev", "3@3.ru",new ArrayList<>());
        userRepository.save(myUserExpected);
        MyUser newUserActual = userRepository.save(myUserExpected);
        assertEquals(myUserExpected,newUserActual);
    }

    @Test
    void saveExceptionTest() {
        MyUser myUserExpected = new MyUser("andrey","testPassword",
                "Andrey", "Zaitsev", "2@2.ru",new ArrayList<>());
        assertThrows(DataIntegrityViolationException.class,
                ()->{ userRepository.save(myUserExpected);
                });
    }

}