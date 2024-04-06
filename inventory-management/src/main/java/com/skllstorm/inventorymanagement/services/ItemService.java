package com.skllstorm.inventorymanagement.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skllstorm.inventorymanagement.dtos.ItemDTO;
import com.skllstorm.inventorymanagement.mappers.ItemMapper;
import com.skllstorm.inventorymanagement.models.Item;
import com.skllstorm.inventorymanagement.models.Warehouse;
import com.skllstorm.inventorymanagement.repos.ItemRepo;
import com.skllstorm.inventorymanagement.repos.WarehouseRepo;

@Service
public class ItemService 
{
    @Autowired
    ItemRepo itemRepo;
    @Autowired
    WarehouseRepo warehouseRepo;
    @Autowired
    ItemMapper itemMapper;

    // CREATE OPERATIONS

    // adds an item
    public String addItem(ItemDTO dto)
    {
        // limits the number of items in a warehouse, no extra items are added into that warehouse
        if (itemRepo.itemsInWarehouse(dto.getWarehouse().getWarehouseId()) <= 20) 
        {
            Optional<Warehouse> warehouse = warehouseRepo.findById(dto.getWarehouse().getWarehouseId());
            Item item = itemMapper.toItem(dto);
            itemRepo.save(item);
            return dto.getItemName() + " HAS BEEN ADDED TO WAREHOUSE " + warehouse.get().getWarehouseName();
        }

        return "WAREHOUSE AT MAX CAPACITY";
    }

    // READ OPERATIONS

    // finds all items in all warehouses in ascending order or item name
    public List<ItemDTO> findAllItems()
    {
        List<Item> item = itemRepo.findAllOrderByItemName();

        List<ItemDTO> dtoList = item.stream().map(itemMapper::toItemDTO).collect(Collectors.toList());

        return dtoList;
    }

    // gets all items in a specific warehouse, never implemented in frontend
    public List<ItemDTO> findAllByWarehouse(int id)
    {
        Optional<Warehouse> warehouse = warehouseRepo.findById(id);
        List<Item> items = itemRepo.findByWarehouse(warehouse.get());
        List<ItemDTO> dtoList = items.stream().map(itemMapper::toItemDTO).collect(Collectors.toList());

        return dtoList;
    }

    // gets the number of items in a warehouse
    // purpose would have been to show the amount of items in a warehouse
    // never implemented in the frontend
    public int itemsInWarehouse(int id)
    {
        int quantity = itemRepo.itemsInWarehouse(id);

        return quantity;
    }

    // UPDATE OPERATIONS

    // updates an item
    public String updateItem(ItemDTO dto)
    {
        int id = dto.getItemId();
        Optional<Item> currentitem = itemRepo.findById(id);

        // makes sure an item already exists that has to be updated
        if (currentitem.isPresent())
        {
            Item item = itemMapper.toItem(dto);
            Optional<Warehouse> warehouse = warehouseRepo.findByWarehouseName(dto.getWarehouse().getWarehouseName());
            item.setWarehouse(warehouse.get());

            itemRepo.save(item);
            ItemDTO itemDTO = itemMapper.toItemDTO(item);
            return "ITEM UPDATED TO " + itemDTO.toString();
        }

        return "ITEM DOES NOT EXIST";
    }

    // DELETE OPERATIONS

    // deletes an item
    public String deleteItem(int id)
    {
        Optional<Item> item = itemRepo.findById(id);

        // checks if the item to be deleted exists
        if (item.isPresent())
        {
            itemRepo.deleteById(id);
            return "ITEM HAS BEEN SHIPPED";
        }

        return "ITEM NOT IN INVENTORY";
    }
}
