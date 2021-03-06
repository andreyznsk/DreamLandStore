package ru.geekbrains.DreamLandStore.serviseImpl.sessionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.jdbc.Sql;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Sql({"/dropTest.sql", "/schemaTest.sql", "/dataTest.sql"})
class SessionsHandlerTest {

    @Autowired
    private SessionsHandler sessionsHandler;

    @Test
    void setMyUserByUser() {
        MyUser myUserExpected = new MyUser(2L, "andrey", "$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.",
                "Andrey", "Zaitsev", "2@2.ru", null,  new ArrayList<>());
        sessionsHandler.setMyUserByUser(new User("andrey", "andrey", new ArrayList<>()));
        MyUser myUserActual = sessionsHandler.getMyUser();
        assertEquals(myUserExpected, myUserActual);

    }

    @Test
    void setAnonymousUser() {
        sessionsHandler.setAnonymousUser();
        MyUser myUserActual = sessionsHandler.getMyUser();
        assertEquals(new MyUser(), myUserActual);
    }

    @Test
    void addTempChart() {

    }

    @Test
    void getCharts() {
        Product product = new Product(1L, "карандаш", new BigDecimal("16.00"));
        Product product1 = new Product(2L, "карандаш2", new BigDecimal("17.00"));
        List<Chart> chartsExpected = new ArrayList<>();
        chartsExpected.add(new Chart(1L, 1L, 1L, product));
        chartsExpected.add(new Chart(2L, 1L, 2L, product1));
        sessionsHandler.setMyUserByUser(new User("admin", "[PROTECTED]", new ArrayList<>()));
        List<Chart> charts = sessionsHandler.getCharts();
        assertEquals(chartsExpected, charts);
    }


    @Test
    void getTotalPrice() {
        sessionsHandler.setMyUserByUser(new User("admin", "[PROTECTED]", new ArrayList<>()));
        double totalPrice = sessionsHandler.getTotalPrice();
        assertEquals(33.0, totalPrice);
    }

    @Test
    void removeChartFromAnonymousUser() {
        List<Chart> chartListExpected = new ArrayList<>();
        chartListExpected.add(new Chart(0L, null, null, null));
        sessionsHandler.setAnonymousUser();
        sessionsHandler.addTempChart(new Chart(0L, null, null, null));
        sessionsHandler.addTempChart(new Chart(0L, null, null, null));
        sessionsHandler.removeChartFromAnonymousUser(1L);
        List<Chart> chartsActual = sessionsHandler.getCharts();
        assertEquals(chartListExpected, chartsActual);

    }

    @Test
    void deleteAll() {
        sessionsHandler.setAnonymousUser();
        sessionsHandler.addTempChart(new Chart());
        sessionsHandler.addTempChart(new Chart());
        sessionsHandler.addTempChart(new Chart());
        sessionsHandler.deleteAllFromTmpChartList();
        List<Chart> charts = sessionsHandler.getCharts();
        assertEquals(new ArrayList<>(),charts);
    }

    @Test
    void getMyUser() {//TODO
        MyUser myUserExpected = new MyUser(1L, "admin", "$2y$12$n7gF2VeEz4ST9MjvdroaBOVClYYO35naUzdr.iHW14Ll42r/JccS.",
                "Andrey", "Zaitsev", "1@1.ru", null, new ArrayList<>());
        sessionsHandler.setMyUserByUser(new User("admin", "[PROTECTED]", new ArrayList<>()));
        MyUser myUserActual = sessionsHandler.getMyUser();
        myUserActual.setRoles(new ArrayList<>());
        System.out.println(myUserActual);
        assertEquals(myUserExpected, myUserActual);
    }
}