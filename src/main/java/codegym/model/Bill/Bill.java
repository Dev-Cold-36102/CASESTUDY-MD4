package codegym.model.Bill;//package com.codegym.model.Bill;
//
//
//import com.codegym.model.Product.Product;
//import com.codegym.model.User.User;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "bill")
//public class Bill {
//    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToMany(mappedBy = "bill")
//    private Set<Product> products;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Set<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Product> products) {
//        this.products = products;
//    }
//
//
//}
