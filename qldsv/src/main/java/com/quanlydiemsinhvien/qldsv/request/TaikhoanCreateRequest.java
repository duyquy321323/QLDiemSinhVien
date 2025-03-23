package com.quanlydiemsinhvien.qldsv.request;

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
public class TaikhoanCreateRequest {
    private String idTaiKhoan;
    private String matKhau;
    private String mkMoi;
    private String xacNhanMk;
}