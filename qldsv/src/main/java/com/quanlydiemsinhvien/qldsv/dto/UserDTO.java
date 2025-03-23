package com.quanlydiemsinhvien.qldsv.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String hoTen;
    private Date ngaySinh;
    private String diaChi;
    private Short gioiTinh;
    private String soDienThoai;
    private String email;
    private TaiKhoanDTO idTaiKhoan;
}