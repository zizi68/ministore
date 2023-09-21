/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author TRINH
 */
public class Province {

    private Integer id;
    private String name;
    private String code;
    private List<District> districts;

    public Province() {
    }

    public Province(String name) {
        this.name = name;
    }

    public Province(Integer id, String name, String code, List<District> districts) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.districts = districts;
    }

    public Integer getProvinceId() {
        return id;
    }

    public void setProvinceId(Integer id) {
        this.id = id;
    }

    public String getProvinceName() {
        return name;
    }

    public void setProvinceName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return code;
    }

    public void setProvinceCode(String code) {
        this.code = code;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @Override
    public String toString() {
        return name;
    }

}
