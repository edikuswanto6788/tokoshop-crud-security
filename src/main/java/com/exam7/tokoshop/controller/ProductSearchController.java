/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.controller;

import com.exam7.tokoshop.entity.Product;
import com.exam7.tokoshop.repository.ProductSearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edi
 */
@RestController
@RequestMapping("/api")
public class ProductSearchController {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    //url search    
    //search?q=samsung&page=0&size=20&sort=PRICE_ASC 
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(
            @RequestParam String q,
            @RequestParam Integer size,
            @RequestParam Integer page,
            @RequestParam String sort) {

        Pageable pageable;
        if ("PRICE_ASC".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price"));
        } else if ("PRICE_DESC".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "price"));
        } else if ("TITLE".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("name"));
        } else {
            pageable = PageRequest.of(page, size);
        }

        List<Product> products = productSearchRepository.findByNameContains(q, pageable);
        for (Product product : products) {
            product.setUser(null);
        }
//        return ResponseEntity.ok(new ArrayList<>(products));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product")
    public ResponseEntity getIdCategory(
            @RequestParam Integer category,
            @RequestParam Integer size,
            @RequestParam Integer page,
            @RequestParam String sort) {
        Pageable pageable;
        if ("PRICE_ASC".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("price"));
        } else if ("PRICE_DESC".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "price"));
        } else if ("TITLE".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("categoryId"));
        } else {
            pageable = PageRequest.of(page, size);
        }
        List<Product> category_product = productSearchRepository.findById(category, pageable);
        for (Product product : category_product) {
            product.setUser(null);
        }

        return ResponseEntity.ok(category_product);
    }

}
