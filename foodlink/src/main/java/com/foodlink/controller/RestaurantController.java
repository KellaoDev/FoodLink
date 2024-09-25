package com.foodlink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurante")
public class RestaurantController {

    @GetMapping("/criar-doacoes")
    public String getRestaurants(Model model) {
        return "/restaurant/createDonations";
    }

    @GetMapping("/minhas-doacoes-realizadas")
    public String getMyDonationsCarriedOut(Model model) {
        return "/restaurant/donationsCarriedOut";
    }
}
