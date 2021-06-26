package ru.geekbrains.DreamLandStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.serviseImpl.userService.UserService;

import java.util.Date;

@RequiredArgsConstructor
@Controller
public class UsersAuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String showUsers(Model model) {
            model.addAttribute("date", new Date());
        return "login";
    }

    @GetMapping("/createUser")
    public String createUser(Model model){
        model.addAttribute("myUser", new MyUser());
        return "createUser";
    }

    @PostMapping("/createUser")
    public String getItemInfo(Model model,MyUser myUser) {
        if(userService.save(myUser)) {
            model.addAttribute("user",myUser.getUsername() );
            model.addAttribute("date", new Date());
            return "redirect:/product";
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:createUser?error");
            return modelAndView.getViewName();
        }




    }
}
