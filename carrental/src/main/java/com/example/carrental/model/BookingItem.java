package com.example.carrental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a single line in a booking.  Each booking item stores
 * the car that was rented, the number of days and the daily price at
 * the time of booking.  Keeping a copy of the price ensures that
 * historical bookings remain accurate even if rental rates change.
 */
@Entity
@Table(name = "booking_items")
public class BookingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Booking booking;
    @ManyToOne
    private Car car;
    private int days;
    private Double price;

    public BookingItem() {
    }

    public BookingItem(Long id, Booking booking, Car car, int days, Double price) {
        this.id = id;
        this.booking = booking;
        this.car = car;
        this.days = days;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}