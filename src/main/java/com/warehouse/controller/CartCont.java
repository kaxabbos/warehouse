package com.warehouse.controller;

import com.warehouse.controller.main.Attributes;
import com.warehouse.model.Cart;
import com.warehouse.model.Product;
import com.warehouse.model.enums.CartStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartCont extends Attributes {
    @GetMapping()
    String cart(Model model) {
        AddAttributesCart(model);
        return "cart";
    }

    @PostMapping("/add/{idProduct}")
    String cartAdd(@RequestParam int quantity, @PathVariable Long idProduct) {
        Product product = productRepo.getReferenceById(idProduct);
        product.addCart(new Cart(quantity, getUser()));
        productRepo.save(product);
        return "redirect:/product/all";
    }

    @PostMapping("/edit/{idCart}")
    String cartEdit(@RequestParam int quantity, @PathVariable Long idCart) {
        Cart cart = cartRepo.getReferenceById(idCart);
        cart.setQuantity(quantity);
        cartRepo.save(cart);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{idCart}")
    String cartDelete(@PathVariable Long idCart) {
        Cart cart = cartRepo.getReferenceById(idCart);
        Product product = cart.getProduct();
        product.removeCart(cart);
        cartRepo.delete(cart);
        productRepo.save(product);
        return "redirect:/cart";
    }

    @GetMapping("/delete/all")
    String cartDeleteAll() {
        List<Cart> cartList = cartRepo.findAllByUser(getUser());
        for (Cart i : cartList) {
            Product product = i.getProduct();
            product.removeCart(i);
            cartRepo.delete(i);
            productRepo.save(product);
        }
        return "redirect:/cart";
    }

    @GetMapping("/wait")
    String ForConfirmation() {
        List<Cart> cartList = cartRepo.findAllByUserAndStatus(getUser(), CartStatus.ISSUED);
        cartList.forEach(cart -> cart.setStatus(CartStatus.WAITING));
        cartRepo.saveAll(cartList);
        return "redirect:/cart";
    }
}
