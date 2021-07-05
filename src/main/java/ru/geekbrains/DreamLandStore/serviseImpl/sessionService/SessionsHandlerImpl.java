package ru.geekbrains.DreamLandStore.serviseImpl.sessionService;

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
public class SessionsHandlerImpl implements SessionsHandler {

    private final UserRepository userRepository;
    private final ChartRepository chartRepository;
    private final ProductRepository productRepository;
    private MyUser myUser;
    private List<Chart> tempChartList = new LinkedList<>();
    private Long localChartID = 0L;
    private boolean isAnonymous = true;

    @Override
    public void setMyUserByUser(User user) {
        this.isAnonymous = false;
        this.myUser = userRepository.findOneByUsername(user.getUsername());
        if(tempChartList.size()!=0) {
            for (Chart chart : tempChartList) {
                chart.setCustomerId(myUser.getId());
                chart.setId(null);
            }
            chartRepository.saveAll(tempChartList);
            this.tempChartList.clear();
        }
    }


    @Override
    public void setAnonymousUser() {
        this.myUser = new MyUser();
    }

    @Override
    public void addTempChart(Chart chart) {
        chart.setId(this.localChartID++);
        this.tempChartList.add(chart);
    }

    @Override
    public List<Chart> getCharts() {
        if(myUser.getUsername() != null){
            return chartRepository.findAllByCustomerId(myUser.getId());
        }
        return tempChartList;
    }

    @Override
    public double getTotalPrice() {
        double tempTotalPrice = 0.0;
        if(myUser.getUsername() != null){
            List<Chart> allByCustomerId = chartRepository.findAllByCustomerId(myUser.getId());
            for (Chart chart : allByCustomerId) {
                tempTotalPrice += chart.getProduct().getPrice().doubleValue();
            }
        } else {
            for (Chart chart : tempChartList) {
                tempTotalPrice += chart.getProduct().getPrice().doubleValue();

            }
        }
        return tempTotalPrice;
    }

    @Override
    public void removeChartFromAnonymousUser(Long id) {
        tempChartList.removeIf(chart -> chart.getId().equals(id));
    }

    @Override
    public void deleteAllFromTmpChartList() {
        this.tempChartList.clear();
    }


}
