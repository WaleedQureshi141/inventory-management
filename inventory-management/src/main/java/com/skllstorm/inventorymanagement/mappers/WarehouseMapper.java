package com.skllstorm.inventorymanagement.mappers;

import org.springframework.stereotype.Component;

import com.skllstorm.inventorymanagement.dtos.WarehouseDTO;
import com.skllstorm.inventorymanagement.models.Warehouse;

// WarehouseMapper is used to convert Warehouse into WarehouseDTO and vice versa
@Component
public class WarehouseMapper 
{
    // converts the WarehouseDTO object to an Warehouse object
    public Warehouse toWarehouse(WarehouseDTO dto)
    {
        return new Warehouse(dto.getWarehouseId(), dto.getWarehouseName(), dto.getLocation());
    }

    // converts the Warehouse object to an WarehouseDTO object
    public WarehouseDTO toWarehouseDTO(Warehouse warehouse)
    {
        return new WarehouseDTO(warehouse.getWarehouseId(), warehouse.getWarehouseName(), warehouse.getLocation());
    }
}
