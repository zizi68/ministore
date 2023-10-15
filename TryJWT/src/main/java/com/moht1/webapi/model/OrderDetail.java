package com.moht1.webapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    private float price;

    @JsonIgnore
    @OneToOne(mappedBy = "orderDetail")
    private Feedback feedback;

    public OrderDetail(Product product, Order order, int quantity, float price) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }

}
