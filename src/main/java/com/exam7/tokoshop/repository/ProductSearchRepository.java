/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.repository;

import com.exam7.tokoshop.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Edi
 */
public interface ProductSearchRepository extends PagingAndSortingRepository<Product, Integer> {
    public List<Product> findByNameContains(String name, Pageable pageable);
    public List<Product> findById(Integer categoryId, Pageable pageable);

}
