package ru.kata.spring.boot_security.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;


@Service
public class Converter {
    private final ModelMapper modelMapper;

    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User userDTOConvertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    public UserDTO userConvertToUserDTO (User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    public RoleDTO roleConvertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
