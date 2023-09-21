/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HP
 */
public class PromotionDetail {
    
    private Integer id;

    private Product product;

    private Promotion promotion;

    private Integer percentage;

    public PromotionDetail() {
    }
    
    public PromotionDetail(Product product, Integer percentage) {
        this.product = product;
        this.percentage = percentage;
    }

    public PromotionDetail(Integer id, Product product, Promotion promotion, Integer percentage) {
        this.id = id;
        this.product = product;
        this.promotion = promotion;
        this.percentage = percentage;
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

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
    
}
