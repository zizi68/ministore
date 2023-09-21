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
public class District {

    private Integer id;
    private String name;
    private String prefix;
    private Province province;
    private List<Ward> wards;

    public District() {
    }

    public District(Integer id, String name, String prefix, Province province, List<Ward> wards) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.province = province;
        this.wards = wards;
    }

    public Integer getDistrictId() {
        return id;
    }

    public void setDistrictId(Integer id) {
        this.id = id;
    }

    public String getDistrictName() {
        return name;
    }

    public void setDistrictName(String name) {
        this.name = name;
    }

    public String getDistrictPrefix() {
        return prefix;
    }

    public void setDistrictPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<Ward> getWards() {
        return wards;
    }

    public void setWards(List<Ward> wards) {
        this.wards = wards;
    }

    @Override
    public String toString() {
        return name; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
