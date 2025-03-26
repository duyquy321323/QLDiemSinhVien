package com.quanlydiemsinhvien.qldsv.converter;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.SinhVienDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Monhocdangky;
import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;
import com.quanlydiemsinhvien.qldsv.repository.LopHocRepository;
import com.quanlydiemsinhvien.qldsv.repository.SinhVienRepository;
import com.quanlydiemsinhvien.qldsv.request.SinhVienCreateRequest;

@Component
public class SinhVienConverter {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LopHocConverter lopHocConverter;

    @Autowired
    private LopHocRepository lopHocRepository;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    public SinhVienDTO sinhVienToSinhVienDTO(Sinhvien sinhvien){
        SinhVienDTO sinhVienDTO = modelMapper.map(sinhvien, SinhVienDTO.class);
        sinhVienDTO.setMaLop(lopHocConverter.lopHocToLopHocDTO(sinhvien.getMaLop()));
        return sinhVienDTO;
    }

    public Sinhvien sinhVienDTOToSinhVien(SinhVienDTO sinhVienDTO){
        Sinhvien sinhvien = sinhVienDTO.getIdSinhVien() == null? null : sinhVienRepository.findById(sinhVienDTO.getIdSinhVien()).orElse(null);
        Set<Monhocdangky> monhocdangkyList = new HashSet<>();
        if(sinhvien != null){
            monhocdangkyList = sinhvien.getMonhocdangkySet();
        }
        Sinhvien newSinhvien = modelMapper.map(sinhVienDTO, Sinhvien.class);
        newSinhvien.setMonhocdangkySet(monhocdangkyList);
        return newSinhvien;
    }

    public SinhVienDTO sinhVienRequestToSinhVienDTO(SinhVienCreateRequest request){
        SinhVienDTO sinhVienDTO = modelMapper.map(request, SinhVienDTO.class);
        sinhVienDTO.setMaLop(lopHocConverter.lopHocToLopHocDTO(lopHocRepository.findById(request.getMaLop()).orElse(null)));
        return sinhVienDTO;
    }
}