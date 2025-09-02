package com.example.carrental;

import com.example.carrental.model.Car;
import com.example.carrental.service.CarService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the Car Rental Spring Boot application.  Annotating
 * the class with {@code @SpringBootApplication} enables auto
 * configuration, component scanning and property support【10272057831146†L265-L278】.  A
 * {@link CommandLineRunner} is defined to seed the database with a few
 * sample cars at startup so you can immediately interact with the
 * application.
 */
@SpringBootApplication
public class CarRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }

    /**
     * Seed the database with some sample cars when the application
     * starts.  This makes it easy to test the API without manually
     * inserting data.
     *
     * @param carService service used to persist cars
     * @return a {@code CommandLineRunner}
     */
    @Bean
    public CommandLineRunner dataLoader(CarService carService) {
        return args -> {
            if (carService.getAllCars().isEmpty()) {
                carService.saveCar(new Car(null, "Toyota Corolla", "Compact sedan", 45.0));
                carService.saveCar(new Car(null, "Ford Fiesta", "Economy hatchback", 35.0));
                carService.saveCar(new Car(null, "BMW 3 Series", "Luxury sedan", 90.0));
            }
        };
    }
}