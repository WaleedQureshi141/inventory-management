package com.skllstorm.inventorymanagement.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skllstorm.inventorymanagement.models.Item;
import com.skllstorm.inventorymanagement.models.Warehouse;

import jakarta.transaction.Transactional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer>
{
    // used to get all items but in alphabetical order of the item name
    @Query(value = "SELECT * FROM items i ORDER BY i.item_name", nativeQuery = true)
    @Transactional
    List<Item> findAllOrderByItemName();

    // never implemented in frontend due to time limit
    @Query(value = "SELECT COUNT(*) FROM items i WHERE i.warehouse_id_fk = ?1", nativeQuery = true)
    @Transactional
    int itemsInWarehouse(int id);       // warehouse id, to check the current items in the warehouse

    // never implemented in frontend due to time limit
    // purpose was to filter items in a specific warehouse
    List<Item> findByWarehouse(Warehouse warehouse);    
}
