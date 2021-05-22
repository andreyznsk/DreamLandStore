package ru.geekbrains.DreamLandStore.serviseImpl;



import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.entry.SystemUser;

public interface UserService extends UserDetailsService {
    MyUser findByUserName(String username);
    boolean save(SystemUser systemUser);
}