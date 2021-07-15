package ru.geekbrains.DreamLandStore.serviseImpl.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.DreamLandStore.model.entry.Role;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.RoleRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;
import ru.geekbrains.DreamLandStore.serviseImpl.sessionService.SessionsHandler;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionsHandler sessionsHandler;


    @Override
    public MyUser findByUserName(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    @Transactional
    public boolean save(MyUser myUser) {
        if (findByUserName(myUser.getUsername()) != null) {
            return false;
        }
        myUser.setPassword(bCryptPasswordEncoder.encode(myUser.getPassword()));
        myUser.setRoles(roleRepository.findOneByName("ROLE_CLIENT"));
        userRepository.save(myUser);
        return true;
    }

    @Override
    public boolean updateUser(MyUser myUser) {
        MyUser myUserTemp = userRepository.findOneByUsername(myUser.getUsername());
        myUser.setPassword(myUserTemp.getPassword());
        myUser.setRoles(myUser.getRoles());
        MyUser save = userRepository.save(myUser);
        sessionsHandler.updateUser(save);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findOneByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(),
                mapRolesToAuthorities(myUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}