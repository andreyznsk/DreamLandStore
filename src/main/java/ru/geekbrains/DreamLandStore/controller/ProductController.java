package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.DreamLandStore.model.entry.Product;
import ru.geekbrains.DreamLandStore.model.repository.ProductRepository;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

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
        return "index";
    }

/*    @GetMapping({"","/{pageId}"})
    public String index(Model model, @PathVariable(required = false) Integer pageId,@RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String sortDirection) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user.getUsername());
        }
        model.addAttribute("date", new Date());
        return "index";
    }*/

}
