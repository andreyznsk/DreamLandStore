package ru.geekbrains.DreamLandStore.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {


    @GetMapping({"","/{pageId}"})
    public String index(Model model, @PathVariable(required = false) Integer pageId,@RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "ASC") String sortDirection) {

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user",user.getUsername() );
        } else model.addAttribute("user",null );


        model.addAttribute("date", new Date());
        return "index";
    }

}
