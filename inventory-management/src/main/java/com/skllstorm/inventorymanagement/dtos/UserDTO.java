package com.skllstorm.inventorymanagement.dtos;

import com.skllstorm.inventorymanagement.models.Warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// UserDTO is a copy of the User object
// created to make sure that only the UserDTO interacts with the frontend
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO 
{
    private int userId;
    private String fname;
    private String lname;
    private String email;
    private String password;
    private String role;
    private Warehouse warehouse;

    public UserDTO(int userId, String fname, String lname, String email, String role, Warehouse warehouse) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
        this.warehouse = warehouse;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
