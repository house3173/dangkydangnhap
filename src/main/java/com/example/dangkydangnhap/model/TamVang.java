package com.example.dangkydangnhap.model;

import java.time.LocalDateTime;

/**
 * Lớp TamVang đại diện cho thông tin về việc tạm vắng của một cư dân trong hệ thống.
 */
public class TamVang {
    private int idTamVang;
    private String soCccd;
    private LocalDateTime ngayBatDau, ngayKetThuc;
    private String liDo;
    
    /**
     * Khởi tạo một đối tượng TamVang mới với thông tin mặc định.
     */
    public TamVang() {
    }
    
    /**
     * Khởi tạo một đối tượng TamVang mới với thông tin được cung cấp.
     *
     * @param idTamVang    ID của thông tin tạm vắng.
     * @param soCccd       Số chứng minh nhân dân của cư dân.
     * @param ngayBatDau   Ngày bắt đầu của thời gian tạm vắng.
     * @param ngayKetThuc  Ngày kết thúc của thời gian tạm vắng.
     * @param liDo         Lý do của việc tạm vắng.
     */
    public TamVang(int idTamVang, String soCccd, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc, String liDo) {
        this.idTamVang = idTamVang;
        this.soCccd = soCccd;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.liDo = liDo;
    }
    
    /**
     * Lấy ID của thông tin tạm vắng.
     *
     * @return ID của thông tin tạm vắng.
     */
    public int getIdTamVang() {
        return idTamVang;
    }
    
    /**
     * Đặt ID mới cho thông tin tạm vắng.
     *
     * @param idTamVang ID mới cho thông tin tạm vắng.
     */
    public void setIdTamVang(int idTamVang) {
        this.idTamVang = idTamVang;
    }
    
    /**
     * Lấy số chứng minh nhân dân của cư dân.
     *
     * @return Số chứng minh nhân dân của cư dân.
     */
    public String getSoCccd() {
        return soCccd;
    }
    
    /**
     * Đặt số chứng minh nhân dân mới cho thông tin tạm vắng.
     *
     * @param soCccd Số chứng minh nhân dân mới cho thông tin tạm vắng.
     */
    public void setSoCccd(String soCccd) {
        this.soCccd = soCccd;
    }
    
    /**
     * Lấy ngày bắt đầu của thời gian tạm vắng.
     *
     * @return Ngày bắt đầu của thời gian tạm vắng.
     */
    public LocalDateTime getNgayBatDau() {
        return ngayBatDau;
    }
    
    /**
     * Đặt ngày bắt đầu mới cho thông tin tạm vắng.
     *
     * @param ngayBatDau Ngày bắt đầu mới cho thông tin tạm vắng.
     */
    public void setNgayBatDau(LocalDateTime ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    
    /**
     * Lấy ngày kết thúc của thời gian tạm vắng.
     *
     * @return Ngày kết thúc của thời gian tạm vắng.
     */
    public LocalDateTime getNgayKetThuc() {
        return ngayKetThuc;
    }
    
    /**
     * Đặt ngày kết thúc mới cho thông tin tạm vắng.
     *
     * @param ngayKetThuc Ngày kết thúc mới cho thông tin tạm vắng.
     */
    public void setNgayKetThuc(LocalDateTime ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    /**
     * Lấy lý do của việc tạm vắng.
     *
     * @return Lý do của việc tạm vắng.
     */
    public String getLiDo() {
        return liDo;
    }
    
    /**
     * Đặt lý do mới cho thông tin tạm vắng.
     *
     * @param liDo Lý do mới cho thông tin tạm vắng.
     */
    public void setLiDo(String liDo) {
        this.liDo = liDo;
    }
}
