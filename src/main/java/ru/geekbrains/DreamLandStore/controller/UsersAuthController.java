package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.function.ServerResponse;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

import java.util.Date;

@Controller
public class UsersAuthController {


    @GetMapping("/login")
    public String showUsers(Model model) {

        model.addAttribute("user","Неизвестный странник");
        model.addAttribute("date", new Date());
        return "login";
    }

   /* @PostMapping({"/login/preform_login","/preform_login"})
    public String showUsersPost(Model model) {

        MyUser myUser = userRepository.findOneByUserName("Andrey");
        model.addAttribute("user",myUser.getUserName());
        model.addAttribute("date", new Date());
        return "login";
    }*/
}
