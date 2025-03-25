package com.quanlydiemsinhvien.qldsv.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.TaiKhoanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import com.quanlydiemsinhvien.qldsv.service.KeycloakUserService;

@Component
public class TaiKhoanConverter {
    @Autowired
    private ModelMapper modelMapper;

    // @Autowired
    // private LoaitaikhoanConverter loaitaikhoanConverter;

    @Autowired
    private KeycloakUserService keycloakUserService;

    public TaiKhoanDTO taiKhoanToTaiKhoanDTO(Taikhoan taikhoan){
        TaiKhoanDTO taiKhoanDTO =  modelMapper.map(taikhoan, TaiKhoanDTO.class);
        taiKhoanDTO.setTenTaiKhoan(keycloakUserService.getUsernameByUserId(taikhoan.getIdTaiKhoan()));
        taiKhoanDTO.setChucVu(keycloakUserService.getUserRoles(taikhoan.getIdTaiKhoan()));
        return taiKhoanDTO;
    }
}