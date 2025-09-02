package com.example.carrental.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a booking cart for the car rental system.  A cart
 * contains one or more {@link CartItem} entries, each referencing a
 * specific car and the number of days it is to be rented.  This
 * mirrors a shopping cart in an e‑commerce site but is adapted for
 * vehicle rentals.
 */
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who owns this cart.  Each user has exactly one
     * active cart.
     */
    @OneToOne
    private User user;

    /**
     * Line items in the cart.  Operations on the cart cascade to
     * its items, and orphan removal ensures that deleting an item
     * from the list removes it from the database.
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
    }

    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    /**
     * Add a car to the cart for a given number of rental days.  If
     * the car already exists in the cart, the rental period is
     * increased accordingly.  Otherwise a new cart item is created.
     *
     * @param car   the car to rent
     * @param days  the number of days to rent the car
     */
    public void addCar(Car car, int days) {
        for (CartItem item : items) {
            if (item.getCar().getId().equals(car.getId())) {
                item.setDays(item.getDays() + days);
                return;
            }
        }
        CartItem newItem = new CartItem();
        newItem.setCart(this);
        newItem.setCar(car);
        newItem.setDays(days);
        items.add(newItem);
    }

    /**
     * Calculate the total cost of the cart based on the daily rate
     * and the number of days for each item.
     *
     * @return the subtotal for the cart
     */
    public double calculateTotal() {
        return items.stream()
                .mapToDouble(item -> item.getCar().getPrice() * item.getDays())
                .sum();
    }
}