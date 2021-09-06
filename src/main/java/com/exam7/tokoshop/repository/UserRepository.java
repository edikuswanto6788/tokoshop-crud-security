/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.repository;

import com.exam7.tokoshop.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Edi
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    
    public User getUserByName (String userName);
    
}
