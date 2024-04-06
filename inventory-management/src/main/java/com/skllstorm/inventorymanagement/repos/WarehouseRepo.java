package com.skllstorm.inventorymanagement.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skllstorm.inventorymanagement.models.Warehouse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Integer>
{

    // both  these methods are used to check if a warehouse name or location have not been taken before
    Optional<Warehouse> findByWarehouseName(String warehouseName);
    Optional<Warehouse> findByLocation(String location);

}
