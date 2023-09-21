/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Password {

    private Long id;
    private String oldPassword;
    private String newPassword;

    public Password() {
    }

    public Password(Long id, String pwdOld, String pwdNew) {
        this.id = id;
        this.oldPassword = pwdOld;
        this.newPassword = pwdNew;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPwdOld() {
        return oldPassword;
    }

    public void setPwdOld(String pwdOld) {
        this.oldPassword = pwdOld;
    }

    public String getPwdNew() {
        return newPassword;
    }

    public void setPwdNew(String pwdNew) {
        this.newPassword = pwdNew;
    }

}
