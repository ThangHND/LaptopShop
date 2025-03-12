package com.example.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.laptopshop.domain.Order;
import com.example.laptopshop.service.ProductService;

@Controller
public class OrderController {
    private final ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/order")
    public String showOrder(Model model) {
        List<Order> list = this.productService.getAllOrder();
        model.addAttribute("order", list);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(@PathVariable long id, Model model) {
        Order order = this.productService.fetchOrderById(id).get();
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        model.addAttribute("orderDetails", order.getDetails());

        return "admin/order/detail";

    }

    @GetMapping("/admin/update/{id}")
    public String getUpdateOrderPage(@PathVariable long id, Model model) {
        Order order = this.productService.fetchOrderById(id).get();
        model.addAttribute("newOrder", order);
        return "admin/order/updateOrder";
    }

    @PostMapping("/admin/update")
    public String handUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.productService.handleSaveOrder(order);
        return "redirect:/admin/order";
    }

}
