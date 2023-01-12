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
        return ResponseEntity.ok(Converter.convertToList(allUsers, converter::userConvertToUserDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {
        if(userServiceImpl.getUserById(id)==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserDTO userDTO = converter.userConvertToUserDTO(userServiceImpl.getUserById(id));
            return ResponseEntity.ok(userDTO);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userServiceImpl.getUser(principal.getName()));
    }


    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<Role> allRoles = userServiceImpl.getAllRoles();
        return ResponseEntity.ok(Converter.convertToList(allRoles, converter::roleConvertToRoleDTO));
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        User newUser = converter.userDTOConvertToUser(userDTO);
        userServiceImpl.saveNewUser(newUser);
        return ResponseEntity.ok(converter.userConvertToUserDTO(newUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("id") int id) {
        User updatedUser = converter.userDTOConvertToUser(userDTO);
        userServiceImpl.updateUser(updatedUser, id);
        return ResponseEntity.ok(converter.userConvertToUserDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
