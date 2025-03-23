package com.quanlydiemsinhvien.qldsv.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SinhVienCreateRequest {
    private Integer idSinhVien;
    private String hoTen;
    private Date ngaySinh;
    private String diaChi;
    private Short gioiTinh;
    private String soDienThoai;
    private String email;
    private Integer maLop;
}