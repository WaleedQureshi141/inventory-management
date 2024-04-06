package com.skllstorm.inventorymanagement.dtos;


import com.skllstorm.inventorymanagement.models.Warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ItemDTO is a copy of the Item object
// created to make sure that only the ItemDTO interacts with the frontend
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO 
{
    private int itemId;
    private String itemName;
    private String description;
    private Warehouse warehouse;
}
