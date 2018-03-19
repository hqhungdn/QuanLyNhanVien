package com.hqhhcmus.model;

/**
 * Created by HQH on 3/14/2018.
 */

public enum ChucVu {
    TRUONGPHONG("Trưởng phòng"), PPHOPHONG("Phó phòng"), NHANVIEN("Nhân viên");
    private String cv;
    ChucVu(String cv){
        this.cv=cv;
    }

    public String getChucVu() {
        return this.cv;
    }
}
