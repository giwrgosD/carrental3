package com.example.carrental.controller;

import com.example.carrental.model.Booking;
import com.example.carrental.model.User;
import com.example.carrental.service.BookingService;
import com.example.carrental.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;

/**
 * REST controller for retrieving booking history.  Authenticated
 * customers can query all bookings they have made.
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping
    public List<Booking> getBookings(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return bookingService.getBookingsForUser(user);
    }
}