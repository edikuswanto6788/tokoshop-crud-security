/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.repository;

import com.exam7.tokoshop.entity.CartItem;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edi
 */
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
   
    @Override
    public Optional<CartItem> findById(Integer id);
}
