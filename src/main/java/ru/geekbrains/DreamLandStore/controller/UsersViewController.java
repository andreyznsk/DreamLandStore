package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("viewUsers")
@RequiredArgsConstructor
public class UsersViewController {

    private final UserRepository userRepository;

   @GetMapping({"","/showAll"})
    public String showUsers(Model model) {
       List<MyUser> myUsers = userRepository.findAll();
       User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       model.addAttribute("user",principal.getUsername());
       model.addAttribute("myUsers",myUsers);
        model.addAttribute("date", new Date());
        return "viewUsers";
    }
}
