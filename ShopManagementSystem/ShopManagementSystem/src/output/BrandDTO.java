/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package output;

import model.Brand;

/**
 *
 * @author HP
 */
public class BrandDTO {
    private Brand brand;
    private Integer soldQuantity;

    public BrandDTO() {
    }

    public BrandDTO(Brand brand, Integer soldQuantity) {
        this.brand = brand;
        this.soldQuantity = soldQuantity;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
    
}
