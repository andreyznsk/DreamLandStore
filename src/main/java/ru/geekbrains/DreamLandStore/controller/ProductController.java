package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;
import ru.geekbrains.DreamLandStore.model.entry.Chart;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.Product;
import ru.geekbrains.DreamLandStore.model.repository.ChartRepository;
import ru.geekbrains.DreamLandStore.model.repository.ProductRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;
import ru.geekbrains.DreamLandStore.model.sessionEntity.SessionUser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ChartRepository chartRepository;
    private final UserRepository userRepository;
    private final SessionUser sessionUser;

    @GetMapping({"","/{pageId}"})
    public String index(Model model, @PathVariable(required = false) Integer pageId,@RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String sortDirection) {
        if (pageId == null) {
            pageId = 1;
        }
        PageRequest pageRequest;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user.getUsername());
            sessionUser.setMyUserByUser(user);
        } else {
            sessionUser.setAnonymousUser(RequestContextHolder.currentRequestAttributes().getSessionId().hashCode());
        }
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        pageRequest = PageRequest.of(pageId - 1,5,sort);
        final Page<Product> productPage = productRepository.findAll(pageRequest);
        model.addAttribute("products", productPage.getContent());
        final int pageNumber = productPage.getPageable().getPageNumber();
        model.addAttribute("currentPage", pageNumber + 1);
        model.addAttribute("previousPage", productPage.getPageable().hasPrevious() ? pageNumber : null);
        model.addAttribute("nextPage", productPage.getTotalPages() > pageNumber + 1 ? pageNumber + 2 : null);
        model.addAttribute("date", new Date());
        model.addAttribute("product", new Product());
        model.addAttribute("sortDirection", sortDirection);
        //SecurityContextHolder.getContext()
        return "welcome";
    }

    @GetMapping("/product/{id}")
    public String getProductById(Model model, @PathVariable Long id){
        Optional<Product> optional = productRepository.findById(id);
        model.addAttribute("product", optional.orElseThrow(IllegalArgumentException::new));
        return "productinfo";

    }

    @GetMapping("/addprod")
    public String getForm(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "addprod";
    }

    @PostMapping("/addprod")
    public String getItemInfo(Model model,Product product) {
        productRepository.save(product);
        model.addAttribute("message", "Сохранено");
        return "addprod";
    }

    @GetMapping("/delete/{id}")
    public String deleteProdById(Model model,@PathVariable Long id){
        productRepository.deleteById(id);
        return "redirect:/app";
    }


    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);
        System.out.println(product);
        model.addAttribute("product",product.orElseThrow(NullPointerException::new));
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateItemForm(Model model, Product product, @PathVariable Long id){
        product.setId(id);
        System.out.println(product);
        productRepository.save (product);
        model.addAttribute("message", "Обновлено");
        return "redirect:/product";
    }

    @GetMapping("/addChart/{id}")
    public String addChartProdById(Model model,@PathVariable Long id){
        Chart chart = new Chart();
        chart.setProdId(id);
        Product product = productRepository.findById(id).orElseThrow(NullPointerException::new);
        chart.setProduct(product);
        chart.setCustomerId(sessionUser.getMyUser().getId());
        if (sessionUser.getMyUser().getUsername() != null) {
            chartRepository.save(chart);
        } else {
            sessionUser.addTempChart(chart);
        }

        return "redirect:/product";
    }

    @GetMapping("/maxprice")
    public String showMaxPrice(Model model, @RequestParam double maxprice ) {
        model.addAttribute("products", productRepository.findAllByPriceBefore(BigDecimal.valueOf(maxprice)));
        return "welcome";
    }

    @GetMapping("/minprice")
    public String showMinPrice(Model model, @RequestParam double minprice) {
        model.addAttribute("products", productRepository.findAllByPriceAfter(BigDecimal.valueOf(minprice)));
        return "view";
    }
    @GetMapping("/btwprice")
    public String showBtwPrice(Model model, @RequestParam double minprice, @RequestParam double maxprice) {
        model.addAttribute("products", productRepository.findAllByPriceBetweenOrderByPrice(BigDecimal.valueOf(minprice),
                BigDecimal.valueOf(maxprice)));
        return "welcome";
    }

}
