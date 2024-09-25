package com.foodlink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ong")
public class OngController {

    @GetMapping("/vizualizar-doacoes")
    public String getOng(Model model) {
        return "/ong/vizualizarDoacoes";
    }

    @GetMapping("/minhas-doacoes")
    public String getMyDonations(Model model) {
        return "/ong/minhasDoacoes";
    }

}
