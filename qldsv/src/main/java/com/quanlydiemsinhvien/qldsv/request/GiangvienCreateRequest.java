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
@NoArgsConstructor
@AllArgsConstructor
public class GiangvienCreateRequest {
    private Integer idGiangVien;
    private String hoTen;
    private Date ngaySinh;
    private String diaChi;
    private Short gioiTinh;
    private String soDienThoai;
    private String email;
}