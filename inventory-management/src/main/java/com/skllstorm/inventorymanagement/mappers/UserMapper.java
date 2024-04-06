package com.skllstorm.inventorymanagement.mappers;

import org.springframework.stereotype.Component;

import com.skllstorm.inventorymanagement.dtos.UserDTO;
import com.skllstorm.inventorymanagement.models.User;

// UserMapper is used to convert User into UserDTO and vice versa
@Component
public class UserMapper 
{
    // converts the UserDTO object to an User object
    public User toUser(UserDTO dto)
    {
        return new User(dto.getUserId(), dto.getFname(), dto.getLname(), dto.getEmail(), dto.getPassword(), dto.getRole(), dto.getWarehouse());
    }

    // converts the UserDTO object to an User object specifically to log in
    public UserDTO toLoginDTO(User user)
    {
        return new UserDTO(user.getEmail(), user.getPassword());
    }

    // converts the User object to an UserDTO object
    // password is never converted to dto so it can stay hidden to db
    public UserDTO toUserDTO(User user)
    {
        return new UserDTO(user.getUserId(), user.getFname(), user.getLname(), user.getEmail(), user.getRole(), user.getWarehouse());
    }
}
