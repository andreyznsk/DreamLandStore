package ru.geekbrains.DreamLandStore.serviseImpl.userService;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.Role;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/drop.sql","/schema.sql","/data.sql"})
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void findByUserName() {
        MyUser myUserExpected = new MyUser(2L,"andrey","$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.",
            "Andrey", "Zaitsev", "2@2.ru",new ArrayList<>());
        MyUser myUserActual = userService.findByUserName("andrey");
        assertEquals(myUserExpected,myUserActual);
    }

    @Test
    void save() {
        MyUser myUserExpected = new MyUser("newUser","admin",
                "Andrey", "Zaitsev", "3@3.ru",new ArrayList<>());
        boolean save = userService.save(myUserExpected);
        assertTrue(save);
    }

    @Test
    void loadUserByUsername() {
        Set<GrantedAuthority> roles = new LinkedHashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        roles.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
        roles.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
        UserDetails myUserExpected = new User("admin","[PROTECTED]",
                true,true,true,true,roles);
        UserDetails myUserActual = userService.loadUserByUsername("admin");
        assertEquals(myUserExpected,myUserActual);
    }
}