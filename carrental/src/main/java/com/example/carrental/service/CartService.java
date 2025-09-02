package com.example.carrental.service;

import com.example.carrental.model.Cart;
import com.example.carrental.model.Car;
import com.example.carrental.model.User;
import com.example.carrental.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for managing the rental cart.  It coordinates the
 * repository and car service to ensure that cars are added to the
 * correct cart and handles creation of carts as needed.
 */
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CarService carService;

    public CartService(CartRepository cartRepository, CarService carService) {
        this.cartRepository = cartRepository;
        this.carService = carService;
    }

    @Transactional
    public Cart getCartForUser(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    /**
     * Add a car to the user's cart for a given number of rental days.
     * Days must be positive.  If the car already exists in the cart,
     * the days are accumulated.
     *
     * @param user   the owner of the cart
     * @param carId  the car identifier
     * @param days   number of days to rent
     */
    @Transactional
    public void addCarToCart(User user, Long carId, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Number of days must be greater than zero");
        }
        Car car = carService.getCarById(carId);
        Cart cart = getCartForUser(user);
        cart.addCar(car, days);
        cartRepository.save(cart);
    }

    @Transactional(readOnly = true)
    public Cart viewCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }
        return cart;
    }

    @Transactional
    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}