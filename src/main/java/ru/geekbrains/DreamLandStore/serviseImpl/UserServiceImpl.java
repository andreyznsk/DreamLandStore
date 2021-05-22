package ru.geekbrains.DreamLandStore.serviseImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.DreamLandStore.model.entry.Role;
import ru.geekbrains.DreamLandStore.model.entry.SystemUser;
import ru.geekbrains.DreamLandStore.model.entry.MyUser;
import ru.geekbrains.DreamLandStore.model.repository.RoleRepository;
import ru.geekbrains.DreamLandStore.model.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public MyUser findByUserName(String username) {
        return userRepository.findOneByUserName(username);
    }

    @Override
    @Transactional
    public boolean save(SystemUser systemUser) {
        MyUser myUser = new MyUser();
        if (findByUserName(systemUser.getUserName()) != null) {
            return false;
        }
        myUser.setUserName(systemUser.getUserName());
        myUser.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        myUser.setFirstName(systemUser.getFirstName());
        myUser.setLastName(systemUser.getLastName());
        myUser.setEmail(systemUser.getEmail());
        myUser.setRoles(Arrays.asList(roleRepository.findOneByName("ROLE_CLIENT")));
        userRepository.save(myUser);
        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findOneByUserName(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(myUser.getUserName(), myUser.getPassword(),
                mapRolesToAuthorities(myUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}