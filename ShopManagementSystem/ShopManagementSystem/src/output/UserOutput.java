/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package output;

import java.util.List;
import model.User;

/**
 *
 * @author PC
 */
public class UserOutput {

    private int page;
    private int totalPage;
    private List<User> listResult;

    public UserOutput() {
    }

    public UserOutput(int page, int totalPage, List<User> listResult) {
        this.page = page;
        this.totalPage = totalPage;
        this.listResult = listResult;
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

    public List<User> getListResult() {
        return listResult;
    }

    public void setListResult(List<User> listResult) {
        this.listResult = listResult;
    }
}
