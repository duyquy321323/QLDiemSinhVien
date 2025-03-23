package com.quanlydiemsinhvien.qldsv.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.MonhocdangkyDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Monhocdangky;

@Component
public class MonHocDangKyConverter {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MonHocHocKyConverter monHocHocKyConverter;

    @Autowired
    private SinhVienConverter sinhVienConverter;

    public MonhocdangkyDTO monhocdangkyToMonhocdangkyDTO(Monhocdangky monhocdangky){
        MonhocdangkyDTO monhocdangkyDTO = modelMapper.map(monhocdangky, MonhocdangkyDTO.class);
        monhocdangkyDTO.setIdMonHoc(monHocHocKyConverter.monhocHockyToMonhocHockyDTO(monhocdangky.getIdMonHoc()));
        monhocdangkyDTO.setIdSinhVien(sinhVienConverter.sinhVienToSinhVienDTO(monhocdangky.getIdSinhVien()));
        return monhocdangkyDTO;
    }
}