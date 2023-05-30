package com.warehouse.controller;

import com.warehouse.controller.main.Attributes;
import com.warehouse.model.Cart;
import com.warehouse.model.Product;
import com.warehouse.model.Stat;
import com.warehouse.model.enums.CartStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/archive")
public class ArchiveCont extends Attributes {
    @GetMapping()
    String cart(Model model) {
        AddAttributesArchive(model);
        return "archive";
    }
}
