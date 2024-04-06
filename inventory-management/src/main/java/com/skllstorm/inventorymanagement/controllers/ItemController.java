package com.skllstorm.inventorymanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skllstorm.inventorymanagement.dtos.ItemDTO;
import com.skllstorm.inventorymanagement.services.ItemService;

@RestController
@RequestMapping("/items")
@CrossOrigin
public class ItemController 
{
    @Autowired
    ItemService service;
    
    // CREATE OPERATIONS

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody ItemDTO dto)
    {
        return new ResponseEntity<>(service.addItem(dto), HttpStatus.OK);
    }

    // READ OPERATIONS

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> findAllItems()
    {
        return new ResponseEntity<>(service.findAllItems(), HttpStatus.FOUND);
    }

    @GetMapping("/warehouse_items/{id}")
    public ResponseEntity<List<ItemDTO>> findItemsInWarehouse(@PathVariable int id)
    {
        return new ResponseEntity<>(service.findAllByWarehouse(id), HttpStatus.FOUND);
    }

    @GetMapping("/warehouse_item_quantity/{id}")
    public ResponseEntity<Integer> warehouseItemQuantity(@PathVariable int id)
    {
        return new ResponseEntity<>(service.itemsInWarehouse(id), HttpStatus.FOUND);
    }

    // UPDATE OPERATIONS

    @PutMapping("/update")
    public ResponseEntity<String> updateItem(@RequestBody ItemDTO dto)
    {
        return new ResponseEntity<>(service.updateItem(dto), HttpStatus.OK);
    }

    // DELETE OPERATIONS

    @DeleteMapping("/ship/{id}")
    public ResponseEntity<String> shipItem(@PathVariable int id)
    {
        return new ResponseEntity<>(service.deleteItem(id), HttpStatus.OK);
    }
}
