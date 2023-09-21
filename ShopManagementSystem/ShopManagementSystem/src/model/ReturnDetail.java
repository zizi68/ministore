/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TRINH
 */
public class ReturnDetail {

    private Integer id;
    private Return return0;
    private Product product;
    private int quantity;

    public ReturnDetail() {
    }

    public ReturnDetail(Integer id, Return return0, Product product, int quantity) {
        this.id = id;
        this.return0 = return0;
        this.product = product;
        this.quantity = quantity;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Return getReturn0() {
        return return0;
    }

    public void setReturn0(Return return0) {
        this.return0 = return0;
    }

}
