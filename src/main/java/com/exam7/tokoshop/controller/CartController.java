/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.controller;

import com.exam7.tokoshop.entity.Cart;
import com.exam7.tokoshop.entity.CartItem;
import com.exam7.tokoshop.entity.Product;
import com.exam7.tokoshop.entity.User;
import com.exam7.tokoshop.repository.CartItemRepository;
import com.exam7.tokoshop.repository.CartRepository;
import com.exam7.tokoshop.repository.ProductRepository;
import com.exam7.tokoshop.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edi
 */
@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/carts")
    public ResponseEntity<String> saveCart(
            @RequestBody Cart cart, Principal principal) {
        String userName = principal.getName();
        User log = userRepository.getUserByName(userName);
        cart.setUser(log);
        cartRepository.save(cart);
        return ResponseEntity.ok("Data berhasil ditambahkan");
    }

    @GetMapping("/allcarts")
    public ResponseEntity<List<CartItem>> getCartItem() {
        Iterable<CartItem> carts = cartItemRepository.findAll();
        List<CartItem> cartList = new ArrayList<>();
        for (CartItem cartI : carts) {
            cartList.add(cartI);

        }
        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(
            @RequestBody CartItem cartItem, 
            @RequestBody Cart cart,
            @RequestBody Product product,Principal principal) {
       
        Integer Id = product.getId();
        Integer IdC = cart.getId();
        Product p = productRepository.findById(Id).get();
        Cart c = cartRepository.findById(IdC).get();
        cartItem.setProduct(p);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        return ResponseEntity.ok("Data Berhasil ditambahkan");

    }

    @PutMapping("/checkout")
    public ResponseEntity<String> Checkout(
            @PathVariable Integer id,           
            Principal principal) {
        String userName = principal.getName();
        User user = userRepository.getUserByName(userName);
        Cart cart = cartRepository.findById(id).get();
        if(cart != null){
            cart.setUser(user);
            cartRepository.save(cart);
        }
        return ResponseEntity.ok("Data berhasil diubah");
    }

}
