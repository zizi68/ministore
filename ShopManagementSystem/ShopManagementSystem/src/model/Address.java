/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author TRINH
 */
public class Address {

    private Integer id;
    private String specificAddress;
    private Ward ward;

    public Address() {
    }

    public Address(Integer id, String specificAddress, Ward ward) {
        this.id = id;
        this.specificAddress = specificAddress;
        this.ward = ward;
    }

    public Integer getAddressId() {
        return id;
    }

    public void setAddressId(Integer id) {
        this.id = id;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

}
