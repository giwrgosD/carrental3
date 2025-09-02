package com.example.carrental.service;

import com.example.carrental.model.Booking;
import com.example.carrental.model.BookingItem;
import com.example.carrental.model.Cart;
import com.example.carrental.model.CartItem;
import com.example.carrental.model.User;
import com.example.carrental.repository.BookingRepository;
import com.example.carrental.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for handling bookings (orders).  Converts cart items
 * into booking items, persists the booking and clears the cart.  Also
 * allows retrieval of booking history.
 */
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public BookingService(BookingRepository bookingRepository,
                          CartService cartService,
                          CartRepository cartRepository) {
        this.bookingRepository = bookingRepository;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    /**
     * Checkout the user's cart, creating a new booking.  Throws an
     * exception if the cart is empty.  Copies each cart item into a
     * booking item and computes the total based on daily rate and
     * rental days.  Clears the cart afterwards.
     *
     * @param user the customer checking out
     * @return the saved booking
     */
    @Transactional
    public Booking checkout(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty. Add cars before checkout.");
        }
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingDate(LocalDateTime.now());
        double total = 0.0;
        for (CartItem cartItem : cart.getItems()) {
            BookingItem bookingItem = new BookingItem();
            bookingItem.setBooking(booking);
            bookingItem.setCar(cartItem.getCar());
            bookingItem.setDays(cartItem.getDays());
            bookingItem.setPrice(cartItem.getCar().getPrice());
            total += cartItem.getCar().getPrice() * cartItem.getDays();
            booking.getItems().add(bookingItem);
        }
        booking.setTotal(total);
        Booking saved = bookingRepository.save(booking);
        cartService.clearCart(cart);
        return saved;
    }

    /**
     * Retrieve all bookings for a given user.
     *
     * @param user the customer
     * @return list of bookings
     */
    @Transactional(readOnly = true)
    public List<Booking> getBookingsForUser(User user) {
        return bookingRepository.findByUser(user);
    }
}