package com.warehouse.controller.main;

import com.warehouse.model.Cart;
import com.warehouse.model.Product;
import com.warehouse.model.Stat;
import com.warehouse.model.enums.CartStatus;
import com.warehouse.model.enums.Category;
import com.warehouse.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("inStock", productRepo.findAll().size());
    }

    protected void AddAttributesStats(Model model) {
        AddAttributes(model);
        List<Product> products = productRepo.findAll();
        Product maxQuantity = products.get(0);
        Product maxPrice = products.get(0);
        Product minPrice = products.get(0);
        List<Category> categories = List.of(Category.values());
        int[] intsQuantity = new int[categories.size()];
        int[] intsPrice = new int[categories.size()];
        int profit = 0;
        for (Product i : products) {
            profit += i.getStat().getPrice();
            if (i.getStat().getQuantity() > maxQuantity.getStat().getQuantity()) {
                maxQuantity = i;
            }
            if (i.getStat().getPrice() > maxPrice.getStat().getPrice()) {
                maxPrice = i;
            }
            if (i.getStat().getPrice() < maxPrice.getStat().getPrice()) {
                minPrice = i;
            }
            for (int j = 0; j < categories.size(); j++) {
                if (categories.get(j).name().equals(i.getCategory().name())) {
                    intsQuantity[j] += i.getStat().getQuantity();
                    intsPrice[j] += i.getStat().getPrice();
                }
            }
        }
        model.addAttribute("profit", profit);
        model.addAttribute("products", products);
        model.addAttribute("maxQuantity", maxQuantity);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("intsQuantity", intsQuantity);
        model.addAttribute("intsPrice", intsPrice);

        String[] topQuantityName = new String[5];
        int[] topQuantityNumber = new int[5];
        List<Stat> stats = new ArrayList<>();
        for (Product i : products) {
            stats.add(i.getStat());
        }
        stats.sort(Comparator.comparing(Stat::getQuantity));
        Collections.reverse(stats);
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topQuantityName[i] = stats.get(i).getProduct().getName();
            topQuantityNumber[i] = stats.get(i).getQuantity();
        }
        model.addAttribute("topQuantityName", topQuantityName);
        model.addAttribute("topQuantityNumber", topQuantityNumber);

        String[] topPriceName = new String[5];
        int[] topPriceNumber = new int[5];
        stats.sort(Comparator.comparing(Stat::getPrice));
        Collections.reverse(stats);
        for (int i = 0; i < stats.size(); i++) {
            if (i == 5) break;
            topPriceName[i] = stats.get(i).getProduct().getName();
            topPriceNumber[i] = stats.get(i).getPrice();
        }
        model.addAttribute("topPriceName", topPriceName);
        model.addAttribute("topPriceNumber", topPriceNumber);
    }

    protected void AddAttributesCart(Model model) {
        AddAttributes(model);
        List<Cart> cartList = cartRepo.findAllByUserAndStatus(getUser(), CartStatus.ISSUED);
        int total = 0;
        for (Cart i : cartList) {
            total += i.getQuantity() * i.getProduct().getPrice();
        }
        model.addAttribute("carts", cartList);
        model.addAttribute("total", total);
    }

    protected void AddAttributesApps(Model model) {
        AddAttributes(model);
        model.addAttribute("carts", cartRepo.findAllByStatus(CartStatus.WAITING));
    }

    protected void AddAttributesArchive(Model model) {
        AddAttributes(model);
        List<Cart> cartList = cartRepo.findAllByUserAndStatus(getUser(), CartStatus.APPROVED);
        cartList.addAll(cartRepo.findAllByUserAndStatus(getUser(), CartStatus.REFUSED));
        model.addAttribute("carts", cartList);
    }

    protected void AddAttributesWarehouse(Model model) {
        AddAttributes(model);
        model.addAttribute("warehouses", warehouseRepo.findAll());
    }

    protected void AddAttributesWarehouseEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("warehouse", warehouseRepo.getReferenceById(id));
    }

    protected void AddAttributesProductAdd(Model model) {
        AddAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("warehouses", warehouseRepo.findAll());
    }

    protected void AddAttributesProductEdit(Model model, Long id) {
        AddAttributes(model);
        model.addAttribute("categories", Category.values());
        model.addAttribute("warehouses", warehouseRepo.findAll());
        model.addAttribute("product", productRepo.getReferenceById(id));
    }

    protected void AddAttributesUsers(Model model) {
        AddAttributes(model);
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", Role.values());
    }

    protected void AddAttributesProducts(Model model) {
        AddAttributes(model);
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", Category.values());
        model.addAttribute("warehouses", warehouseRepo.findAll());
    }

    protected void AddAttributesProductsCategory(Model model, Category category) {
        AddAttributesProducts(model);
        model.addAttribute("products", productRepo.findAllByCategory(category));
        model.addAttribute("selectedCategory", category);
    }

    protected void AddAttributesProductsWarehouse(Model model, Long warehouse) {
        AddAttributesProducts(model);
        model.addAttribute("products", warehouseRepo.getReferenceById(warehouse).getProducts());
        model.addAttribute("selectedW", warehouse);
    }

    protected void AddAttributesProductsSearch(Model model, Category category, String search, String desk, Long warehouseId) {
        AddAttributesProducts(model);

        List<Product> products = warehouseRepo.getReferenceById(warehouseId).getProducts();
        products.sort(Comparator.comparing(Product::getPrice));

        if (!desk.equals("cheap")) {
            Collections.reverse(products);
        }

        products = products.stream().filter(product -> product.getCategory() == category && product.getName().contains(search)).toList();

        model.addAttribute("products", products);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedW", warehouseId);
        model.addAttribute("input", search);
    }
}
