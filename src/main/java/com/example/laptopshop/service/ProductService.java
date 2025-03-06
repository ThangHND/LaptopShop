package com.example.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.laptopshop.domain.Cart;
import com.example.laptopshop.domain.CartDetail;
import com.example.laptopshop.domain.Product;
import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.CartDetailRepository;
import com.example.laptopshop.repository.CartRepository;
import com.example.laptopshop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    // logic add product to cart
    public void handleAddProductCart(String email, long productId) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user co cart hay chua ? neu chua -> tao moi
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // tao moi cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }
            // save cart_detail
            Optional<Product> product = this.productRepository.findById(productId);
            if (product.isPresent()) {
                Product realProduct = product.get();

                //
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);

                    this.cartDetailRepository.save(cartDetail);

                    // update cart
                    cart.setSum(cart.getSum() + 1);
                    this.cartRepository.save(cart);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }

        // luu cart_detail
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);

    }

    @Transactional
    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartDetail.getCart();

            // Cập nhật giỏ hàng trước khi xóa CartDetail
            if (currentCart != null) {
                if (currentCart.getSum() > 1) {
                    int s = currentCart.getSum() - 1;
                    currentCart.setSum(s);
                    session.setAttribute("sum", s);
                    this.cartRepository.save(currentCart); // Lưu lại Cart trước khi xóa CartDetail
                } else {
                    session.setAttribute("sum", 0);
                    this.cartRepository.deleteById(currentCart.getId()); // Xóa luôn Cart nếu không còn sản phẩm
                }
            }

            // Xóa CartDetail
            this.cartDetailRepository.deleteById(cartDetailId);
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCardDetail = cdOptional.get();
                currentCardDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCardDetail);
            }
        }
    }
}