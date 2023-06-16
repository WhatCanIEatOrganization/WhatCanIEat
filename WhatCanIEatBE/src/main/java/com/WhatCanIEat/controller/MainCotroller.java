package com.WhatCanIEat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainCotroller {


    @GetMapping
    public String viewHomePage() {
        return "View Home Page";
    }
}
