package ru.geekbrains.DreamLandStore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

@Controller
@RequestMapping("")
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String showUsers(Model model) {
        MyUser myUser = userRepository.findOneByUserName("Andrey");
        System.out.println(myUser);
        return "index";
    }
}
