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
public class Ward {

    private Integer id;
    private String name;
    private String prefix;
    private District district;
    private List<Address> addresses;

    public Ward() {
    }

    public Ward(Integer id, String name, String prefix, District district, List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.district = district;
        this.addresses = addresses;
    }

    public Integer getWardId() {
        return id;
    }

    public void setWardId(Integer id) {
        this.id = id;
    }

    public String getWardName() {
        return name;
    }

    public void setWardName(String name) {
        this.name = name;
    }

    public String getWardPrefix() {
        return prefix;
    }

    public void setWardPrefix(String prefix) {
        this.prefix = prefix;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return name; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
