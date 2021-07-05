package ru.geekbrains.DreamLandStore.model.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/dropTest.sql", "/schemaTest.sql", "/dataTest.sql"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserNameTest() {
        MyUser myUserExpected = new MyUser(2L,"andrey","$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.",
                "Andrey", "Zaitsev", "2@2.ru", null,new ArrayList<>());
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
                "Andrey", "Zaitsev", "3@3.ru", "null" ,new ArrayList<>());
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

    @Test
    void updateTest() {
        MyUser myUserExpected = new MyUser("newUser", "testPassword",
                "Andrey", "Zaitsev", "3@3.ru", null);
        MyUser newUserActual = userRepository.save(myUserExpected);
        myUserExpected.setAddress("Novosibirsk");
        myUserExpected.setId(newUserActual.getId());
        newUserActual = userRepository.save(myUserExpected);
        assertEquals(myUserExpected, newUserActual);
    }


}