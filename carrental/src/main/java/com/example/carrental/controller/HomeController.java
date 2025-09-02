package com.example.carrental.controller;

import com.example.carrental.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page.  Displays the list of available cars
 * using a Thymeleaf template.  HTML controllers are kept separate
 * from REST controllers.
 */
@Controller
public class HomeController {
    private final CarService carService;

    public HomeController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Show the home page listing all cars.
     *
     * @param model the model supplying data to the view
     * @return the name of the Thymeleaf template to render
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "home";
    }
}