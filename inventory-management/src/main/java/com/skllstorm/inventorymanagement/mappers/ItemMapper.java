package com.skllstorm.inventorymanagement.mappers;

import org.springframework.stereotype.Component;

import com.skllstorm.inventorymanagement.dtos.ItemDTO;
import com.skllstorm.inventorymanagement.models.Item;

// ItemMapper is used to convert Item into ItemDTO and vice versa
@Component
public class ItemMapper 
{
    // converts the ItemDTO object to an Item object
    public Item toItem(ItemDTO dto)
    {
        return new Item(dto.getItemId(), dto.getItemName(), dto.getDescription(), dto.getWarehouse());
    }

    // converts the Item object to an ItemDTO object
    public ItemDTO toItemDTO(Item item)
    {
        return new ItemDTO(item.getItemId(), item.getItemName(), item.getDescription(), item.getWarehouse());
    }
}
