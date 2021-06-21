package ru.geekbrains.DreamLandStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.ProductRepository;
import ru.geekbrains.DreamLandStore.model.sessionEntity.SessionUser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product/chart")
public class ChartController {

    private final ChartRepository chartRepository;
    private final SessionUser sessionUser;

    @GetMapping("")
    public String chart(Model model){
        List<Chart> charts = sessionUser.getCharts();
        model.addAttribute("charts", charts);
        model.addAttribute("user", sessionUser.getMyUser().getUsername());
        model.addAttribute("date",new Date());
        double totalPrice = sessionUser.getTotalPrice();
        model.addAttribute("totalPrice", totalPrice);
        return "chart_view";
    }

    @GetMapping("/delete/{id}")
    public String deleteProdById(Model model,@PathVariable Long id){
        if (sessionUser.getMyUser().getUsername()!=null) {
            Chart chart = chartRepository.findById(id).orElseThrow(NullPointerException::new);
            chartRepository.delete(chart);
        } else {
            sessionUser.removeChart(id);
        }
        return "redirect:/product/chart";
    }

    @GetMapping("/deleteAll")
    public String deleteAll(Model model){
        if (sessionUser.getMyUser().getUsername()!=null) {
            chartRepository.deleteAllByCustomerId(sessionUser.getMyUser().getId());
        } else {
            sessionUser.deleteAll();
        }
        return "redirect:/product/chart";
    }

}
