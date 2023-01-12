package ru.kata.spring.boot_security.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
//    public RoleDTO roleConvertToRoleDTO(Role role) {
//        return modelMapper.map(role, RoleDTO.class);
//    }

    public RoleDTO roleConvertToRoleDTO(Role role) {
//        return userServiceImpl.getAllRoles()
//                .stream().modelMapper.map(role, RoleDTO.class))
//                .collect(Collectors.toSet());
        RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
       return roleDTO;
    }

    public static <R, E> List<R> convertToList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }

//    public static <R, E> Set<R> convertToSet(Set<E> list, Function<E, R> converter) {
//        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toSet());
//    }
}
