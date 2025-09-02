package com.example.carrental.repository;

import com.example.carrental.model.Cart;
import com.example.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link Cart} entities.  Allows lookup by
 * owner.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}