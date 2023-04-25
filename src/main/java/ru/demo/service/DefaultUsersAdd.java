package ru.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.demo.repository.RoleRepository;
import ru.demo.repository.UserRepository;
import ru.demo.model.Role;
import ru.demo.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class DefaultUsersAdd {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultUsersAdd(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public void defaultUsers () {
        Set<Role> rolesAdmin = new HashSet<>();
        Role adminRole = new Role("ROLE_ADMIN");
        User adminUser = new User("admin", "admin_surname", 35, "HR", passwordEncoder.encode("admin"));

        rolesAdmin.add(adminRole);
        adminUser.setRoles(rolesAdmin);
        roleRepository.save(adminRole);
        userRepository.save(adminUser);


        Set<Role> rolesUser = new HashSet<>();
        Role userRole = new Role("ROLE_USER");
        User userUser = new User("user", "user_surname", 40, "IT",passwordEncoder.encode("user"));

        rolesUser.add(userRole);
        userUser.setRoles(rolesUser);
        roleRepository.save(userRole);
        userRepository.save(userUser);

        Set<Role> rolesCombo = new HashSet<>();
        User userCombo = new User("combo", "combo_surname", 10, "Sales",passwordEncoder.encode("combo"));
        rolesCombo.add(userRole);
        rolesCombo.add(adminRole);
        userCombo.setRoles(rolesCombo);
        userRepository.save(userCombo);
    }
}
