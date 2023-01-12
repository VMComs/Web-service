package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.Converter;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
public class RestUserController {

    private final UserServiceImpl userServiceImpl;
    private final Converter converter;

    public RestUserController(UserServiceImpl userServiceImpl, Converter converter) {
        this.userServiceImpl = userServiceImpl;
        this.converter = converter;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allUsers = userServiceImpl.findAll();
        return new ResponseEntity<>(Converter.convertToList(allUsers, converter::userConvertToUserDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {
        if(userServiceImpl.getUserById(id)==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserDTO userDTO = converter.userConvertToUserDTO(userServiceImpl.getUserById(id));
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser(Principal principal) {
        return new ResponseEntity<>(userServiceImpl.getUser(principal.getName()), HttpStatus.OK);
    }


    @GetMapping("/roles")
    public ResponseEntity<Set<RoleDTO>> getAllRoles() {
        Set<Role> allRoles = userServiceImpl.getAllRoles();
        return new ResponseEntity<>(Converter.convertToSet(allRoles, converter::roleConvertToRoleDTO), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        User newUser = converter.userDTOConvertToUser(userDTO);
        userServiceImpl.saveNewUser(newUser);
        return new ResponseEntity<>(converter.userConvertToUserDTO(newUser), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") int id) {
        User updatedUser = converter.userDTOConvertToUser(userDTO);
        userServiceImpl.updateUser(updatedUser, id);
        return new ResponseEntity<>(converter.userConvertToUserDTO(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
