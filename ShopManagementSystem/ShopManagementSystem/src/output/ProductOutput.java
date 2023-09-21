/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package output;

import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author TRINH
 */
public class ProductOutput {

    private int page;
    private int totalPage;
    private List<Product> listResult;

    public ProductOutput() {
        listResult = new ArrayList<>();
    }

    public ProductOutput(int page, int totalPage) {
        this.page = page;
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Product> getListResult() {
        return listResult;
    }

    public void setListResult(List<Product> listResult) {
        this.listResult = listResult;
    }

}
