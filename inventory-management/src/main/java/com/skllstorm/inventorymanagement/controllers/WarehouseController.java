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

import com.skllstorm.inventorymanagement.dtos.WarehouseDTO;
import com.skllstorm.inventorymanagement.services.WarehouseService;

@RestController
@RequestMapping("/warehouses")
@CrossOrigin
public class WarehouseController 
{
    @Autowired
    WarehouseService warehouseService;
    
    // CREATE OPERATIONS

    @PostMapping("/add")
    public ResponseEntity<String> createWarehouse(@RequestBody WarehouseDTO dto)
    {
        return new ResponseEntity<>(warehouseService.addWarehouse(dto), HttpStatus.OK);
    }

    // READ OPERATIONS

    @GetMapping("/all")
    public ResponseEntity<List<WarehouseDTO>> allWarehouses()
    {
        return new ResponseEntity<>(warehouseService.findAllWarehouse(), HttpStatus.FOUND);
    }

    // UPDATE OPERATIONS

    @PutMapping("/update")
    public ResponseEntity<String> updateWarehouse(@RequestBody WarehouseDTO dto)
    {
        return new ResponseEntity<>(warehouseService.upadteWarehouse(dto), HttpStatus.OK);
    }

    // DELETE OPERATIONS

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable int id)
    {
        return new ResponseEntity<>(warehouseService.delWarehouse(id), HttpStatus.OK);
    }
}
