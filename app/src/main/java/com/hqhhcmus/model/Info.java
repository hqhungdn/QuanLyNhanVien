package com.hqhhcmus.model;

import java.io.Serializable;

/**
 * Created by HQH on 3/14/2018.
 */

public class Info implements Serializable {
    private String ma;
    private String name;

    public Info() {
    }

    public Info(String ma, String name) {
        this.ma = ma;
        this.name = name;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
