package com.example.carrental.controller;

import com.example.carrental.model.Cart;
import com.example.carrental.model.Booking;
import com.example.carrental.model.User;
import com.example.carrental.service.CartService;
import com.example.carrental.service.BookingService;
import com.example.carrental.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

/**
 * REST controller for cart interactions.  Endpoints require
 * authentication via HTTP Basic.  The {@link Principal} parameter
 * exposes the username of the authenticated user.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final BookingService bookingService;

    public CartController(CartService cartService, UserService userService, BookingService bookingService) {
        this.cartService = cartService;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public Cart viewCart(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return cartService.viewCart(user);
    }

    /**
     * Add a car to the user's cart.  The parameter {@code days}
     * represents how many days the car will be rented.  This is
     * analogous to quantity in a shopping cart.
     *
     * @param carId  the car's ID
     * @param days   number of rental days
     * @param principal supplies the authenticated username
     * @return the updated cart
     */
    @PostMapping("/add")
    public Cart addCarToCart(@RequestParam Long carId,
                             @RequestParam int days,
                             Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        cartService.addCarToCart(user, carId, days);
        return cartService.viewCart(user);
    }

    /**
     * Checkout the cart and create a booking.  After checkout the
     * cart is cleared.
     *
     * @param principal authenticated user
     * @return the created booking
     */
    @PostMapping("/checkout")
    public Booking checkout(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return bookingService.checkout(user);
    }
}