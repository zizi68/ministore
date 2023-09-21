/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package output;

import java.util.ArrayList;
import java.util.List;
import model.Brand;

/**
 *
 * @author TRINH
 */
public class BrandOutput {

    private int page;
    private int totalPage;
    private List<Brand> listResult;

    public BrandOutput() {
        listResult = new ArrayList<>();
    }

    public BrandOutput(int page, int totalPage) {
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

    public List<Brand> getListResult() {
        return listResult;
    }

    public void setListResult(List<Brand> listResult) {
        this.listResult = listResult;
    }

}
