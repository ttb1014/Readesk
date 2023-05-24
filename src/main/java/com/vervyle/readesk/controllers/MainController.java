package com.vervyle.readesk.controllers;

import com.vervyle.readesk.services.ListingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private ListingsService listingsService;

    @Autowired
    public void setListingsService(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ListingsService.Listing> listings = listingsService.getListings();
        model.addAttribute("listings", listings);
        return "home";
    }
}
