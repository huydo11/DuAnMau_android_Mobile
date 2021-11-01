package com.example.duanmau_android_mobile.model;

public class LoaiSach {
    private int maLoai;
    private String tenLoai;
    private String nhaCungCap;


    public LoaiSach() {
    }

    public LoaiSach(int maLoai, String tenLoai, String nhaCungCap) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.nhaCungCap = nhaCungCap;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }
}
