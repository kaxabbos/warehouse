package com.warehouse.controller;

import com.warehouse.controller.main.Attributes;
import com.warehouse.model.User;
import com.warehouse.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UsersCont extends Attributes {
    @GetMapping("/all")
    String users(Model model) {
        AddAttributesUsers(model);
        return "users";
    }

    @PostMapping("/edit/{id}")
    String userEdit(Model model, @RequestParam Role role, @PathVariable Long id) {
        User user = userRepo.getReferenceById(id);
        user.setRole(role);
        userRepo.save(user);
        return "redirect:/user/all";
    }

    @GetMapping("/delete/{id}")
    String userDelete(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/user/all";
    }
}
