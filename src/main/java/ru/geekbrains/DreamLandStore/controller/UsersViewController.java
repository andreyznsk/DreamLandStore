package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/viewUsers")
@RequiredArgsConstructor
public class UsersViewController {

    private final UserRepository userRepository;
    private final SessionsHandler sessionsHandler;

   @GetMapping("")
   @PreAuthorize(value = "login")
    public String showUsers(Model model) {
       List<MyUser> myUsers = userRepository.findAll();
       User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       model.addAttribute("user",principal.getUsername());
       model.addAttribute("myUsers",myUsers);
       model.addAttribute("date", new Date());
       return "viewUsers";
    }
}
