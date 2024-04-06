package com.skllstorm.inventorymanagement.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.skllstorm.inventorymanagement.models.User;
import com.skllstorm.inventorymanagement.models.Warehouse;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>
{

    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findByWarehouse(Warehouse warehouse);
    Optional<User> findByEmail(String email);
    
    // makes sure only the user role can be updated and nothing else
    @Query(value = "UPDATE users SET role = ?1 WHERE user_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    public int updateRole(String role, int id);
}
