/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package output;

import java.util.ArrayList;
import java.util.List;
import model.Category;

/**
 *
 * @author TRINH
 */
public class CategoryOutput {

    private int page;
    private int totalPage;
    private List<Category> listResult;

    public CategoryOutput() {
        listResult = new ArrayList<>();
    }

    public CategoryOutput(int page, int totalPage) {
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

    public List<Category> getListResult() {
        return listResult;
    }

    public void setListResult(List<Category> listResult) {
        this.listResult = listResult;
    }

}
