package com.skllstorm.inventorymanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skllstorm.inventorymanagement.dtos.UserDTO;
import com.skllstorm.inventorymanagement.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    UserService userService;

    // CREATE OPERATIONS

    @PostMapping("/new_user")
    public ResponseEntity<String> createNewUser(@RequestBody UserDTO dto)       // REQUESTBODY
    {
        String result = userService.createUser(dto);
        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }

    // READ OPERATIONS

    @GetMapping("/all_users")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return new ResponseEntity<List<UserDTO>>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> userById(@PathVariable int id)
    {
        UserDTO user = userService.findById(id);
        if (user != null)
        {
            return new ResponseEntity<UserDTO>(user, HttpStatus.FOUND);
        }

        return new ResponseEntity<UserDTO>(user, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO dto)
    {
        String email = dto.getEmail();
        String password = dto.getPassword();
        UserDTO user = userService.findLogin(email, password);
        if (user != null)
        {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<List<UserDTO>> usersInWarehouse(@PathVariable int id)
    {
        return new ResponseEntity<>(userService.findAllByWarehouse(id), HttpStatus.FOUND);
    }

    // UPDATE OPERATIONS

    @PutMapping("/update_user")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO dto)          // REQUESTBODY
    {
        return new ResponseEntity<String>(userService.updateUser(dto), HttpStatus.FOUND);
    }

    // DELETE OPERATIONS

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id)
    {
        return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.FOUND);
    }
}
