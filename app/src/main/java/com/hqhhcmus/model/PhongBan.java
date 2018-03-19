package com.hqhhcmus.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HQH on 3/14/2018.
 */

public class PhongBan extends Info implements Serializable {
    private  ArrayList<NhanVien> listNhanvien=new ArrayList<>();

    public PhongBan(String ma, String name) {
        super(ma, name);
    }

    public PhongBan() {
    }

    public ArrayList<NhanVien> getListNhanvien() {
        return listNhanvien;
    }

    public void setListNhanvien(ArrayList<NhanVien> listNhanvien) {
        this.listNhanvien = listNhanvien;
    }
    public NhanVien getNhanvien(int index){
        return this.listNhanvien.get(index);
    }
    public int size(){
        return this.listNhanvien.size();
    }
    public void themNhanvien(NhanVien nhanVien){
        listNhanvien.add(nhanVien);

    }

    //Hàm trả về trưởng phòng, 1 phognf ban có 1 trưởng phòng, nếu không có thì trả về null
    public NhanVien getTruongPhong(){
        for(int i=0;i<listNhanvien.size();i++){
            NhanVien nhanVien=listNhanvien.get(i);
            if(nhanVien.getChucvu()==ChucVu.TRUONGPHONG){
                return nhanVien;
            }
        }
        return null;
    }
}
