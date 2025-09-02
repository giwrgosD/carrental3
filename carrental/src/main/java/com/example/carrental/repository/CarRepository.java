package com.example.carrental.repository;

import com.example.carrental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Car} entities.  Extending
 * {@link JpaRepository} gives CRUD operations without the need for
 * boilerplate code【10272057831146†L227-L241】.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}