package com.hqhhcmus.model;

import java.io.Serializable;

/**
 * Created by HQH on 3/14/2018.
 */

public class NhanVien extends Info implements Serializable{
    private boolean gioitinh;
    private ChucVu chucvu;
    private PhongBan phongban;

    public NhanVien(String ma, String name) {
        super(ma, name);
    }

    public NhanVien() {
        super();
    }


    public NhanVien(String ma, String name, boolean gioitinh, ChucVu chucvu, PhongBan phongban) {
        super(ma, name);
        this.gioitinh = gioitinh;
        this.chucvu = chucvu;
        this.phongban = phongban;
    }


    public NhanVien(String ma, String name, boolean gioitinh) {
        super(ma, name);
        this.gioitinh = gioitinh;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public ChucVu getChucvu() {
        return chucvu;
    }

    public void setChucvu(ChucVu chucvu) {
        this.chucvu = chucvu;
    }

    public PhongBan getPhongban() {
        return phongban;
    }

    public void setPhongban(PhongBan phongban) {
        this.phongban = phongban;
    }
}
