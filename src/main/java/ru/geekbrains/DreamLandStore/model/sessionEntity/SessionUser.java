package ru.geekbrains.DreamLandStore.model.sessionEntity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.ProductRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;


@Component
@Scope(
        value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
@Data
@RequiredArgsConstructor
public class SessionUser {

    private final UserRepository userRepository;
    private final ChartRepository chartRepository;
    private final ProductRepository productRepository;
    private MyUser myUser;
    private List<Chart> tempChart = new LinkedList<>();
    private Long localID = 0L;

    public void setMyUserByUser(User user) {
        this.myUser = userRepository.findOneByUsername(user.getUsername());
        if(tempChart.size()!=0) {
            for (Chart chart : tempChart) {
                chart.setCustomerId(myUser.getId());
                chart.setId(null);
                chartRepository.save(chart);
            }
            this.tempChart.clear();
        }
    }


    public void setAnonymousUser(int sessionId) {
        this.myUser = new MyUser();
        this.myUser.setId((long)sessionId);
    }

    public void addTempChart(Chart chart) {
        chart.setId(localID++);
        this.tempChart.add(chart);
    }

    public List<Chart> getCharts() {
        if(myUser.getUsername() != null){
            return chartRepository.findAllByCustomerId(myUser.getId());
        }
        return tempChart;
    }

    public double getTotalPrice() {
        double tempTotalPrice = 0.0;
        if(myUser.getUsername() != null){
            List<Chart> allByCustomerId = chartRepository.findAllByCustomerId(myUser.getId());
            for (Chart chart : allByCustomerId) {
                tempTotalPrice += chart.getProduct().getPrice().doubleValue();
            }
        } else {
            for (Chart chart : tempChart) {
                tempTotalPrice += chart.getProduct().getPrice().doubleValue();

            }
        }
        return tempTotalPrice;
    }

    public void removeChart(Long id) {
        tempChart.removeIf(chart -> chart.getId().equals(id));
    }

    public void deleteAll() {
        this.tempChart.clear();
    }
}
