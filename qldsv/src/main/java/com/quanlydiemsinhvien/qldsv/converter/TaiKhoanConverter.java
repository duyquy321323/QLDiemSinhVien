package com.quanlydiemsinhvien.qldsv.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.TaiKhoanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;

@Component
public class TaiKhoanConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LoaitaikhoanConverter loaitaikhoanConverter;

    public TaiKhoanDTO taiKhoanToTaiKhoanDTO(Taikhoan taikhoan){
        TaiKhoanDTO taiKhoanDTO =  modelMapper.map(taikhoan, TaiKhoanDTO.class);
        taiKhoanDTO.setChucVu(loaitaikhoanConverter.loaitaikhoanToLoaitaikhoanDTO(taikhoan.getChucVu()));
        return taiKhoanDTO;
    }
}