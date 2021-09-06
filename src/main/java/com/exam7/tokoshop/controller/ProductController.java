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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(productRepository.findById(Id).get());
    }

    @PostMapping("/product") // mapping for insert product
    public ResponseEntity<String> saveProduct(
            @RequestBody Product product) {
        //Dummy before we work on login
        Iterable<User> users = userRepository.findAll();
        User loggedInUser = null;
        for (User user : users) {
            loggedInUser = user;
            break;
        }
        product.setUser(loggedInUser);
        productRepository.save(product);
        //pretend we save it to database
        return ResponseEntity.ok("Data Berhasil Di Simpan");
    }

    @PutMapping("/product") // Mapping for update product
    public ResponseEntity<String> updateProduct(
            @RequestBody Product product) {
        productRepository.save(product);
        //pretend we save it to database
        return ResponseEntity.ok("Data Berhasil di Update");
    }

    @PutMapping("/product/stock/{id}") // Mapping for update stock y id
    public ResponseEntity<String> updateStockById(
            @PathVariable Integer id, @RequestBody Stock stock) {
        Product product = productRepository.findById(id).get();

        if (product != null) {
            product.setStock(stock.getStock());
            productRepository.save(product);
        }
        return ResponseEntity.ok("Berhasil update Stock");
    }

    @DeleteMapping("/product/person/{id}")
    public ResponseEntity<String> deleteProductById(
            @PathVariable(name = "id") Integer id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Data Berhasil di Hapus berdasarkan Id");
    }

}
