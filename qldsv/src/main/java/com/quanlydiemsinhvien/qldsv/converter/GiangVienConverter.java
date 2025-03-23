package com.quanlydiemsinhvien.qldsv.converter;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.GiangVienDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Giangvien;
import com.quanlydiemsinhvien.qldsv.pojo.MonhocHocky;
import com.quanlydiemsinhvien.qldsv.repository.GiangvienRepository;
import com.quanlydiemsinhvien.qldsv.request.GiangvienCreateRequest;

@Component
public class GiangVienConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GiangvienRepository giangvienRepository;

    @Autowired
    private TaiKhoanConverter taiKhoanConverter;

    public GiangVienDTO giangVienToGiangVienDTO(Giangvien giangVien){
        GiangVienDTO giangVienDTO = modelMapper.map(giangVien, GiangVienDTO.class);
        giangVienDTO.setIdTaiKhoan(giangVien.getIdTaiKhoan() == null? null : taiKhoanConverter.taiKhoanToTaiKhoanDTO(giangVien.getIdTaiKhoan()));
        return giangVienDTO;
    }

    public Giangvien giangVienCreateRequestToGiangVien(GiangvienCreateRequest request){
        Giangvien giangvien = request.getIdGiangVien() == null? null : giangvienRepository.findById(request.getIdGiangVien()).orElse(null);
        Set<MonhocHocky> monhocHockyList = new HashSet<>();
        if(giangvien != null){
            monhocHockyList = giangvien.getMonhocHockySet();
        }
        Giangvien newGiangvien = modelMapper.map(request, Giangvien.class);
        newGiangvien.setMonhocHockySet(monhocHockyList);
        return newGiangvien;
    }
}