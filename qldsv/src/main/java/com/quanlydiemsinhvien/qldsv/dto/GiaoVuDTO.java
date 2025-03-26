package com.quanlydiemsinhvien.qldsv.dto;

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
public class GiaoVuDTO {
    private String idGiaoVu;
    private String tenGiaoVu;
    private short gioiTinh;
    private String soDienThoai;
    private String idTaiKhoan;
}