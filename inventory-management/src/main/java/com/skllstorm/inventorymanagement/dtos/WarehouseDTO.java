package com.skllstorm.inventorymanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// WarehouseDTO is a copy of the Warehouse object
// created to make sure that only the WarehouseDTO interacts with the frontend
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO 
{
    private int warehouseId;
    private String warehouseName;
    private String location;
}
