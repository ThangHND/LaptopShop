package com.example.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.laptopshop.domain.Cart;
import com.example.laptopshop.domain.CartDetail;
import com.example.laptopshop.domain.Order;
import com.example.laptopshop.domain.OrderDetail;
import com.example.laptopshop.domain.Product;
import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.CartDetailRepository;
import com.example.laptopshop.repository.CartRepository;
import com.example.laptopshop.repository.OrderDetailRepository;
import com.example.laptopshop.repository.OrderRepository;
import com.example.laptopshop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService,
            OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartDetail.getCart();

            // Xóa CartDetail trước
            this.cartDetailRepository.deleteById(cartDetailId);

            // Cập nhật giỏ hàng sau khi xóa CartDetail
            if (currentCart != null) {
                int newSum = currentCart.getSum() - 1;
                if (newSum > 0) {
                    currentCart.setSum(newSum);
                    session.setAttribute("sum", newSum);
                    this.cartRepository.save(currentCart);
                } else {
                    session.setAttribute("sum", 0);
                    this.cartRepository.deleteById(currentCart.getId());
                }
            }
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

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        // create order_detail

        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                Order order = new Order();
                order.setUser(user);

                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");
                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);
                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order); // gan toi tuong => thuc chat trong table se luu id
                    orderDetail.setProducts(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }
                this.cartRepository.deleteById(cart.getId());

                // step 3: update session
                session.setAttribute("sun", 0);
            }
        }
    }

    public List<Order> getAllOrder() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void handleSaveOrder(Order order) {
        Optional<Order> optional = this.fetchOrderById(order.getId());
        if (optional.isPresent()) {
            Order currentOrder = optional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.getOrderByUser(user);
    }
}