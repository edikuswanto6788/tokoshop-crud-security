/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edi
 */
@Entity
@Table (name="t_cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column (name = "status", length=255, nullable = false)
    private String status;
    
    @Column (name = "transaction_date", nullable = false)
    private Date transationDate;
    
    @ManyToOne // user setiap user dapat memiliki lebih ati 1 brang
    @JoinColumn (name = "id_user", nullable = false)
    private User user;
    
    @OneToMany (mappedBy = "cart", cascade= CascadeType.ALL)//setiap banyak barang hanya memiliki 1 transaksi
    private List<CartItem> cartItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTransationDate() {
        return transationDate;
    }

    public void setTransationDate(Date transationDate) {
        this.transationDate = transationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }
}
