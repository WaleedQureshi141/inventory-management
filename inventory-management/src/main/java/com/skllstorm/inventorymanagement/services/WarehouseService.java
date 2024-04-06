package com.skllstorm.inventorymanagement.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skllstorm.inventorymanagement.dtos.WarehouseDTO;
import com.skllstorm.inventorymanagement.mappers.WarehouseMapper;
import com.skllstorm.inventorymanagement.models.Warehouse;
import com.skllstorm.inventorymanagement.repos.WarehouseRepo;

@Service
public class WarehouseService 
{
    @Autowired
    WarehouseRepo warehouseRepo;
    @Autowired
    WarehouseMapper warehouseMapper;
    
    // CREATE OPERATIONS

    // adds a new warehouse
    public String addWarehouse(WarehouseDTO dto)
    {
        // checking if warehouse already exists
        // this is done as an extra step to make sure the warehouse name and location are unique
        Optional<Warehouse> wareByName = warehouseRepo.findByWarehouseName(dto.getWarehouseName());
        Optional<Warehouse> wareByLocation = warehouseRepo.findByLocation(dto.getLocation());

        if (wareByName.isPresent() || wareByLocation.isPresent())
        {
            return "WAREHOUSE ALREADY EXISTS";
        }

        Warehouse warehouse = warehouseMapper.toWarehouse(dto);
        warehouseRepo.save(warehouse);

        return "WAREHOUSE CREATED";
    }

    // READ OPERATIONS

    // gets all warehouses
    public List<WarehouseDTO> findAllWarehouse()
    {
        List<Warehouse> warehouses = warehouseRepo.findAll();
        List<WarehouseDTO> dtoList = warehouses.stream().map(warehouseMapper::toWarehouseDTO).collect(Collectors.toList());
        return dtoList;
    }

    // UPADTE OPERATIONS

    // updates a warehouse
    public String upadteWarehouse(WarehouseDTO dto)
    {
        Optional<Warehouse> warehouse = warehouseRepo.findById(dto.getWarehouseId());

        // checks if a warehouse is present to update
        if (!warehouse.isPresent())
        {
            return "WAREHOUSE DOES NOT EXIST";
        }

        Warehouse saved = warehouseMapper.toWarehouse(dto);
        warehouseRepo.save(saved);

        WarehouseDTO warehouseDTO = warehouseMapper.toWarehouseDTO(saved);

        return "WAREHOUSE UPDATED TO " + warehouseDTO.toString();
    }

    // DELETE OPERATIONS

    // deletes a warehouse
    public String  delWarehouse(int id)
    {
        // checks if the warehouse to be deleted exists
        Optional<Warehouse> check = warehouseRepo.findById(id);
        if (check.isPresent())
        {
            warehouseRepo.delete(check.get());
            return "WAREHOUSE HAS BEEN DELETED";
        }

        return "WAREHOUSE DOES NOT EXIST";
    }
}
