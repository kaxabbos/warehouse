package com.warehouse.controller;

import com.warehouse.controller.main.Attributes;
import com.warehouse.model.Warehouse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/warehouse")
public class WarehouseCont extends Attributes {
    @GetMapping
    String warehouse(Model model) {
        AddAttributesWarehouse(model);
        return "warehouses";
    }

    @GetMapping("/add")
    String warehouseAdd(Model model) {
        AddAttributes(model);
        return "warehouseAdd";
    }

    @PostMapping("/add")
    String productAddNew(@RequestParam String name, @RequestParam String address, @RequestParam String description) {
        warehouseRepo.save(new Warehouse(name, address, description));
        return "redirect:/warehouse";
    }

    @GetMapping("/edit/{id}")
    String warehouseEdit(Model model, @PathVariable Long id) {
        AddAttributesWarehouseEdit(model, id);
        return "warehouseEdit";
    }

    @PostMapping("/edit/{id}")
    String productEditOld(@PathVariable Long id, @RequestParam String name, @RequestParam String address, @RequestParam String description) {
        Warehouse warehouse = warehouseRepo.getReferenceById(id);
        warehouse.setName(name);
        warehouse.setAddress(address);
        warehouse.setDescription(description);
        warehouseRepo.save(warehouse);
        return "redirect:/warehouse";
    }

    @GetMapping("/delete/{id}")
    String warehouseDelete(@PathVariable Long id) {
        warehouseRepo.deleteById(id);
        return "redirect:/warehouse";
    }

}
