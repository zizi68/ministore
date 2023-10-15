package com.moht1.webapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "return_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "return_id")
    private Return return0;

    private int quantity;

    public ReturnDetail(Product product, Return return0, int quantity, float price) {
        this.product = product;
        this.return0 = return0;
        this.quantity = quantity;
    }

}
