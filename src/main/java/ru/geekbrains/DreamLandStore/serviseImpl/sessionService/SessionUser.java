package ru.geekbrains.DreamLandStore.serviseImpl.sessionService;

import org.springframework.security.core.userdetails.User;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;

import javax.sql.RowSet;
import java.util.List;

public interface SessionUser {
    void setMyUserByUser(User user);

    void setAnonymousUser();

    void addTempChart(Chart chart);

    List<Chart> getCharts();

    double getTotalPrice();

    void deleteAllFromTmpChartList();

    MyUser getMyUser();

    void removeChartFromAnonymousUser(Long id);
}
