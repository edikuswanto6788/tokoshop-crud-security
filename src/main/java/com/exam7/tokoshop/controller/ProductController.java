/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.controller;

import com.exam7.tokoshop.entity.Product;
import com.exam7.tokoshop.entity.User;
import com.exam7.tokoshop.model.Stock;
import com.exam7.tokoshop.repository.ProductRepository;
import com.exam7.tokoshop.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct() {
        Iterable<Product> products = productRepository.findAll();
        List<Product> productList = new ArrayList<>();
        for (Product product : products) {
            productList.add(product);

        }
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/product/id/{id}") // mapping for show product by id
    public ResponseEntity<Product> getProductById(
            @PathVariable(name = "id") Integer Id) {

        Optional<Product> optionalProduct = productRepository.findById(Id);
        if (!optionalProduct.isEmpty()) {
            return ResponseEntity.ok(optionalProduct.get());
        }
        return ResponseEntity.
                badRequest()
                .body(null);

    }

    @PostMapping("/product") // mapping for insert product
    public ResponseEntity<String> saveProduct(
            @RequestBody Product product, Principal principal) {
        //Dummy before we work on login
        String userName = principal.getName();
        User loggedInUser = userRepository.getUserByName(userName);
        product.setUser(loggedInUser);
        productRepository.save(product);
        //pretend we save it to database
        return ResponseEntity.ok("Data Berhasil Di Simpan");
    }

    @PutMapping("/product") // Mapping for update product
    public ResponseEntity<String> updateProduct(
            @RequestBody Product product, Principal principal) {
        String userName = principal.getName();
        User user = userRepository.getUserByName(userName);
        if (user != null) {
            Product p = productRepository.findById(product.getId()).get();
            if (p != null
                    && p.getUser() != null
                    && user.getId().equals(p.getUser().getId())) {
                product.setUser(user);
                productRepository.save(product);
                //pretend we save it to database
                return ResponseEntity.ok("Data Berhasil di Update");
            }
        }
        return badRequest();
    }

    @PutMapping("/product/stock/{id}") // Mapping for update stock y id
    public ResponseEntity<String> updateStockById(
            @PathVariable Integer id,
            @RequestBody Stock stock,
            Principal principal) {
//        String userName = principal.getName();
//        User user = userRepository.getUserByName(userName);
//        if (user != null) {
//            Optional<Product> optionalProduct = productRepository.findById(id);
//            if (optionalProduct.isEmpty() == false) {
//                Product product = optionalProduct.get();
//                if (product != null
//                        && product.getUser() != null
//                        && product.getUser().getId().equals(user.getId())) {
//                    product.setStock(stock.getStock());
//                    productRepository.save(product);
//                    return ResponseEntity.ok("Berhasil update Stock");
//                }
//
//            }
        Product product = getProductByIdAndUserName(id, principal.getName());
        if (product != null) {
            product.setStock(stock.getStock());
            productRepository.save(product);
            return ResponseEntity.ok("Berhasil update Stock");

        }
        return badRequest();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(
            @PathVariable(name = "id") Integer id, Principal principal) {
//        String userName = principal.getName();
//        User user = userRepository.getUserByName(userName);
//        if (user != null) {
//            Product product = productRepository.findById(id).get();
//            if (product != null
//                    && product.getUser() != null
//                    && product.getUser().getId().equals(user.getId())) {
//                product.setUser(user);
        Product product = getProductByIdAndUserName(id, principal.getName());
        if (product != null) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Data Berhasil di Hapus ");
        }
        return badRequest();
    }

    //methode untuk memanggil productById and us
    public Product getProductByIdAndUserName(Integer id, String userName) {
        User user = userRepository.getUserByName(userName);
        if (user != null) {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (!optionalProduct.isEmpty()) {
                Product product = optionalProduct.get();
                if (product != null && product.getUser() != null
                        && product.getUser().getId().equals(user.getId())) {
                    return product;
                }
            }
        }
        return null;
    }

    public ResponseEntity<String> badRequest() {
        return ResponseEntity.badRequest().body("invalid id parameter or product is not allowed to delete with this user");

    }
}
