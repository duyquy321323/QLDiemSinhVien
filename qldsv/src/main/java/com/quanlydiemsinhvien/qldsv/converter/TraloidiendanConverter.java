package com.quanlydiemsinhvien.qldsv.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.TraloidiendanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Traloidiendan;

@Component
public class TraloidiendanConverter {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaiKhoanConverter taiKhoanConverter;

    @Autowired
    private CauhoidiendangConverter cauhoidiendangConverter;

    public TraloidiendanDTO traloidiendanToTraloidiendanDTO(Traloidiendan traloidiendan){
        TraloidiendanDTO traloidiendanDTO = modelMapper.map(traloidiendan, TraloidiendanDTO.class);
        traloidiendanDTO.setIdCauHoiDienDan(cauhoidiendangConverter.cauhoidiendangToCauhoidiendangDTO(traloidiendan.getIdCauHoiDienDan()));
        traloidiendanDTO.setIdTaiKhoan(taiKhoanConverter.taiKhoanToTaiKhoanDTO(traloidiendan.getIdTaiKhoan()));
        return traloidiendanDTO;
    }
}