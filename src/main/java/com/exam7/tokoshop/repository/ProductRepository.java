/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.repository;

import com.exam7.tokoshop.entity.Product;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edi
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Override
    @Cacheable(value = "getProducts")
    public Iterable<Product> findAll();

    @Override
    @Cacheable(value = "getProductById", key = "#id") //untuk menangkap redis berdasarakan key
    public Optional<Product> findById(Integer id);
    
    
    

}
