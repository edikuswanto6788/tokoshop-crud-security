/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.repository;

import com.exam7.tokoshop.entity.Cart;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edi
 */
public interface CartRepository extends CrudRepository<Cart, Integer> {

    @Override
    public Optional<Cart> findById(Integer Id);
}
