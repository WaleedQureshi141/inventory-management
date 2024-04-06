package com.skllstorm.inventorymanagement.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skllstorm.inventorymanagement.dtos.UserDTO;
import com.skllstorm.inventorymanagement.mappers.UserMapper;
import com.skllstorm.inventorymanagement.models.User;
import com.skllstorm.inventorymanagement.models.Warehouse;
import com.skllstorm.inventorymanagement.repos.UserRepo;
import com.skllstorm.inventorymanagement.repos.WarehouseRepo;

@Service
public class UserService 
{
    @Autowired
    UserRepo userRepo;
    @Autowired
    WarehouseRepo warehouseRepo;
    @Autowired
    UserMapper userMapper;

    // CREATE OPERATIONS

    // adds a user
    public String createUser(UserDTO dto)
    {
        Optional<User> checkUser = userRepo.findByEmail(dto.getEmail());
        // checks if a user email address already exists to avoid duplicates
        if (!checkUser.isPresent())
        {
            User user = userMapper.toUser(dto);
            userRepo.save(user);
            return "USER CREATED";
        }

        return "USER ALREADY EXISTS";
    }

    // READ OPERATIONS

    // finds all the users in the db
    public List<UserDTO> findAllUsers()
    {
        List<User> users = userRepo.findAll();

        List<UserDTO> dtoList = users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());

        return dtoList;
    }

    // finds a user with a specific id
    public UserDTO findById(int id)
    {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent())
        {
            UserDTO dto = userMapper.toUserDTO(user.get());
            return dto;
        }
        return null;
    }

    // compares the user email and password for logging in
    public UserDTO findLogin(String email, String password)
    {
        Optional<User> checkUser = userRepo.findByEmailAndPassword(email, password);
        if (checkUser.isPresent())
        {
            UserDTO dto = userMapper.toUserDTO(checkUser.get());
            return dto;
        }

        return null;
    }

    // would have been used to check the users that worked in a specific warehouse
    public List<UserDTO> findAllByWarehouse(int id)
    {
        Optional<Warehouse> warehouse = warehouseRepo.findById(id);
        List<User> users = userRepo.findByWarehouse(warehouse.get());
        List<UserDTO> dtoList = users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());

        return dtoList;
    }

    // UPDATE OPERATIONS

    // updates a user
    public String updateUser(UserDTO dto)
    {
        int id = dto.getUserId();
        Optional<User> currentUser = userRepo.findById(id);
        // checks if the user to be updated exists
        if (currentUser.isPresent())
        {
            userRepo.updateRole(dto.getRole(), id);
            return "USER ROLE SET TO " + dto.getRole();
        }

        return "USER DOES NOT EXIST";
    }

    // DELETE OPERATIONS

    // deletes a user
    public String deleteUser(int id)
    {
        Optional<User> user = userRepo.findById(id);
        // checks if the user to be deleted exists
        if (user.isPresent())
        {
            userRepo.deleteById(id);
            return "USER DELETED";
        }

        return "USER DOES NOT EXIST";
    }
}
