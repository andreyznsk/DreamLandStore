package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.entry.Order;
import ru.geekbrains.DreamLandStore.model.entry.OrderDetails;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.OrderDetailRepository;
import ru.geekbrains.DreamLandStore.model.repository.OrderRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product/order")
public class OrderController {

    private final SessionsHandler sessionsHandler;
    private final OrderRepository orderRepository;
    private final ChartRepository chartRepository;
    private final OrderDetailRepository orderDetailRepository;

    @GetMapping("/processOrder")
    public String viewOrders(){
        if (sessionsHandler.isAnonymous()) {
            return "redirect:/login";
        }
        List<Chart> allChartsByCustomerId = chartRepository.findAllByCustomerId(sessionsHandler.getMyUser().getId());
        if(allChartsByCustomerId.size() == 0) {
            return "redirect:/product";
        }
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setCustomerId(sessionsHandler.getMyUser().getId());
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (Chart chart : allChartsByCustomerId) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setProdId(chart.getProdId());
            orderDetails.setProduct(chart.getProduct());
            orderDetailsList.add(orderDetails);
        }
        order.setOrderDetailsList(orderDetailsList);
        order.setTotalPrice(BigDecimal.valueOf(sessionsHandler.getTotalPrice()));
        Order save1 = orderRepository.save(order);
        if (save1.getOrderId() != null){
            chartRepository.deleteAll(allChartsByCustomerId);
        }
        return "redirect:/product/order/view";
    }


    @GetMapping("/view")
    public String viewOrder(Model model){
        if (sessionsHandler.isAnonymous()) {
            return "redirect:/login";
        }
        List<Order> orders  = orderRepository.findAllByCustomerId(sessionsHandler.getMyUser().getId());
        model.addAttribute("orders", orders);
        model.addAttribute("user", sessionsHandler.getMyUser().getUsername());
        model.addAttribute("date",new Date());
        return "orders_view";
    }

    @GetMapping("/remove/{id}")
    public String removeOrder(Model model,@PathVariable Long id){
        orderRepository.deleteById(id);
        List<Order> orders  = orderRepository.findAllByCustomerId(sessionsHandler.getMyUser().getId());
        model.addAttribute("orders", orders);
        model.addAttribute("user", sessionsHandler.getMyUser().getUsername());
        model.addAttribute("date",new Date());
        return "orders_view";
    }

    @GetMapping("/update/{id}")
    public String viewOrderDetail(Model model,@PathVariable Long id){
        List<OrderDetails> orderDetailsList = orderDetailRepository.findAllByOrderId(id);
        Optional<Order> byId = orderRepository.findById(id);
        if(orderDetailsList.size()>0){
            model.addAttribute("user", sessionsHandler.getMyUser().getUsername());
            model.addAttribute("orderNumber", byId.orElseThrow(NullPointerException::new).getOrderId());
            model.addAttribute("orderDetails", orderDetailsList);
            model.addAttribute("totalPrice",byId.orElseThrow(NullPointerException::new).getTotalPrice());
            model.addAttribute("date",new Date());
        }

        return "order_details_view";
    }




}
