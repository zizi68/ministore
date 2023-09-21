/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package output;

import model.Category;

/**
 *
 * @author HP
 */
public class CategoryDTO {
    private Category category;
    private Integer soldQuantity;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category, Integer soldQuantity) {
        this.category = category;
        this.soldQuantity = soldQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
    
}
