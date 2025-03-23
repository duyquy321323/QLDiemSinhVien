package com.quanlydiemsinhvien.qldsv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaiKhoanDTO {
    private String idTaiKhoan;
    private String tenTaiKhoan;
    private String matKhau;
    private String image;
    private LoaitaikhoanDTO chucVu;
}
