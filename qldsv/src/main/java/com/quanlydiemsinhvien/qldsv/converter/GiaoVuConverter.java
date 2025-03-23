package com.quanlydiemsinhvien.qldsv.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.GiaoVuDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Giaovu;

@Component
public class GiaoVuConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaiKhoanConverter taiKhoanConverter;

    public GiaoVuDTO giaoVuToGiaoVuDTO(Giaovu giaovu) {
        GiaoVuDTO giaoVuDTO = modelMapper.map(giaovu, GiaoVuDTO.class);
        giaoVuDTO.setIdTaiKhoan(taiKhoanConverter.taiKhoanToTaiKhoanDTO(giaovu.getIdTaiKhoan()));
        return giaoVuDTO;
    }
}