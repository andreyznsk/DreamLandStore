package ru.geekbrains.DreamLandStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;
import ru.geekbrains.DreamLandStore.serviseImpl.userService.UserService;

import java.util.Date;


@Controller
@RequestMapping("/userDetails")
@RequiredArgsConstructor
public class UsersDetailsController {

    private final SessionsHandler sessionsHandler;
    private final UserService userService;

    @GetMapping("/viewDetails")
    public String showUsersDetails(Model model) {
        if(sessionsHandler.isAnonymous()) {
            return "redirect:/login";
        }
        MyUser myUser = sessionsHandler.getMyUser();
        model.addAttribute("user",myUser.getUsername());
        model.addAttribute("myUser",myUser);
        model.addAttribute("date", new Date());
        return "viewUserDetails";
    }

    @PostMapping("/update/{id}")
    public String updateUserDetails(@PathVariable Long id, MyUser myUser){
        myUser.setId(id);
        myUser.setUsername(sessionsHandler.getMyUser().getUsername());
        userService.updateUser(myUser);
        return "redirect:/userDetails/viewDetails";

    }


}
