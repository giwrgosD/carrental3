package com.example.carrental.repository;

import com.example.carrental.model.Booking;
import com.example.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for {@link Booking} entities.  Provides a method to
 * retrieve all bookings made by a specific user.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}