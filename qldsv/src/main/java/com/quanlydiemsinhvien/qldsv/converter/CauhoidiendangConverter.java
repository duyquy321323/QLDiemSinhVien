package com.quanlydiemsinhvien.qldsv.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.CauhoidiendangDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang;

@Component
public class CauhoidiendangConverter {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaiKhoanConverter taiKhoanConverter;

    public CauhoidiendangDTO cauhoidiendangToCauhoidiendangDTO(Cauhoidiendang cauhoidiendang){
        CauhoidiendangDTO cauhoidiendangDTO = modelMapper.map(cauhoidiendang, CauhoidiendangDTO.class);
        cauhoidiendangDTO.setIdTaiKhoan(taiKhoanConverter.taiKhoanToTaiKhoanDTO(cauhoidiendang.getIdTaiKhoan()));
        return cauhoidiendangDTO;
    }
}