package ru.kata.spring.boot_security.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositoriy.RoleRepository;
import ru.kata.spring.boot_security.demo.repositoriy.UserRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    public List<User> findAll () {
        return userRepository.findAll();
    }


    public User getUserById(int id) {
        Optional<User> user = userRepository.findUserById(id);
        return user.orElse(null);
    }

    @Transactional
    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, int id) {
    user.setId(id);
    userRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFound = userRepository.findUserByName(username);
        if (userFound.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found!", username));
        }
        return userFound.get();
    }

    public User getUser(String username) {
        return userRepository.findUserByName(username).get();
    }

    public List<Role> getAllRoles() {
        return new ArrayList<>(roleRepository.findAll());
    }
}
