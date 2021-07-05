package ru.geekbrains.DreamLandStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product/chart")
public class ChartController {

    private final ChartRepository chartRepository;
    private final SessionsHandler sessionsHandler;

    @GetMapping("")
    public String chart(Model model){
        List<Chart> charts = sessionsHandler.getCharts();
        model.addAttribute("charts", charts);
        model.addAttribute("user", sessionsHandler.getMyUser().getUsername());
        model.addAttribute("date",new Date());
        double totalPrice = sessionsHandler.getTotalPrice();
        model.addAttribute("totalPrice", totalPrice);
        return "chart_view";
    }

    @GetMapping("/delete/{id}")
    public String deleteProdById(Model model,@PathVariable Long id){
        if (sessionsHandler.getMyUser().getUsername()!=null) {
            Chart chart = chartRepository.findById(id).orElseThrow(NullPointerException::new);
            chartRepository.delete(chart);
        } else {
            sessionsHandler.removeChartFromAnonymousUser(id);
        }
        return "redirect:/product/chart";
    }

    @GetMapping("/deleteAll")
    public String deleteAll(Model model){
        if (sessionsHandler.getMyUser().getUsername()!=null) {
            chartRepository.deleteAllByCustomerId(sessionsHandler.getMyUser().getId());
        } else {
            sessionsHandler.deleteAllFromTmpChartList();
        }
        return "redirect:/product/chart";
    }

}
