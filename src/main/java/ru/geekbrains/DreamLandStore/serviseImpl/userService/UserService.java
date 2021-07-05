package ru.geekbrains.DreamLandStore.serviseImpl.userService;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;

public interface UserService extends UserDetailsService {
    MyUser findByUserName(String username);
    boolean save(MyUser systemUser);
    boolean updateUser(MyUser myUser);
}