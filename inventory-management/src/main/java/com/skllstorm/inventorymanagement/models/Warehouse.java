package com.skllstorm.inventorymanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouses")
public class Warehouse 
{
    // sets the warehouse_id as the primary key
    @Id
    @Column(name = "warehouse_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    // makes sure that the warehouse name is unique
    @Column(name = "warehouse_name", unique = true)
    private String warehouseName;

    // makes sure that the warehouse location is also unique
    @Column(nullable = false, unique = true)
    private String location;
}
