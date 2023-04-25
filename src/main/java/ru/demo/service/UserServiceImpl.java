package ru.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.repository.RoleRepository;
import ru.demo.repository.UserRepository;
import ru.demo.model.Role;
import ru.demo.model.User;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passEncoder = passEncoder;
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
        user.setPassword(passEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(User user, int id) {
        User oldUser = userRepository.getById(id);
        if (!user.getPassword().equals(oldUser.getPassword())) {
            user.setPassword(passEncoder.encode(user.getPassword()));
        }
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
