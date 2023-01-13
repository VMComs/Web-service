package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFound = userRepository.findUserByName(username);
        if (userFound == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
//        User user = userFound.get();
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return userFound.get();
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

//    @Transactional
//    Collection<? extends GrantedAuthority> roleToAuthorities(User user) {
//
//        List<SimpleGrantedAuthority> lst = user.getRoles().stream()
//                .map(r -> new SimpleGrantedAuthority(r.getRole()))
//                .collect(Collectors.toList());
//        lst.add(new SimpleGrantedAuthority(String.valueOf(user.getId())));
//        return lst;
//    }
}
