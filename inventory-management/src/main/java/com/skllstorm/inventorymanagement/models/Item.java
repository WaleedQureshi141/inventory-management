package com.skllstorm.inventorymanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item 
{
    // sets the item_id as the primary key
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    // nullable makes sure that the item_name is not left empty
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column
    private String description;

    // creates a many to one relationship between item and warehouse table
    // uses the warehouse_id as foreign key
    @ManyToOne
    @JoinColumn(name = "warehouse_id_fk")
    private Warehouse warehouse;
}
