/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlydiemsinhvien.qldsv.converter.GiangVienConverter;
import com.quanlydiemsinhvien.qldsv.dto.GiangVienDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Giangvien;
import com.quanlydiemsinhvien.qldsv.repository.GiangvienRepository;
import com.quanlydiemsinhvien.qldsv.service.GiangVienService;
import com.quanlydiemsinhvien.qldsv.service.KeycloakUserService;

/**
 *
 * @author FPTSHOP
 */
@Service
@Transactional
public class GiangVienServiceImpl implements GiangVienService {

    @Autowired
    private GiangvienRepository giangVienRepository;

    @Autowired
    private KeycloakUserService keycloakUserService;
    
    @Autowired
    private GiangVienConverter giangVienConverter;

    @Override
    public Page<GiangVienDTO> getGiangvienList(Map<String, String> params, int page, int pageSize) {
        try{
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            String tenGV = params.get("tenGV");

            if(tenGV != null){
                Page<Giangvien> giangVienList = giangVienRepository.findByHoTenContaining(tenGV, pageable);
                return giangVienList.map(it -> it != null ? giangVienConverter.giangVienToGiangVienDTO(it) : null);
            }
            Page<Giangvien> giangVienList = giangVienRepository.findAll(pageable);
            return giangVienList.map(it -> it != null ? giangVienConverter.giangVienToGiangVienDTO(it) : null);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addOrUpdateGiangVien(Giangvien gv, Principal principal) {
        try{
            giangVienRepository.save(gv);
            if(gv != null && gv.getIdTaiKhoan() != null){
                keycloakUserService.updateUser(principal.getName(), gv.getEmail(), gv.getHoTen());
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GiangVienDTO getGiangVienById(Integer idGiangVien) {
        return giangVienConverter.giangVienToGiangVienDTO(giangVienRepository.findById(idGiangVien).orElseThrow(() -> new RuntimeException("Giảng viên không tồn tại!")));
    }
    
    @Override
    public boolean deleteById(Integer idGiangVien) {
        try {
            Giangvien giangvien = giangVienRepository.findById(idGiangVien).orElse(null);
            if(giangvien != null && giangvien.getIdTaiKhoan() != null){
                keycloakUserService.deleteUser(giangvien.getIdTaiKhoan());
            }
            giangVienRepository.deleteGiangVienById(idGiangVien);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting GiangVien with id: " + idGiangVien);
            return false;
        }
    }
    
    @Override
    public GiangVienDTO getGiangVienByIdTaiKhoan(String idTaiKhoan) {
        return giangVienConverter.giangVienToGiangVienDTO(giangVienRepository.findByIdTaiKhoan(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản này không phải là giảng viên!")));
    }


    @Override
    public long countGiangVien() {
        return this.giangVienRepository.count();
    }

    @Override
    public List<GiangVienDTO> getGiangvienCCTKList() {
        return giangVienRepository.findAll().stream().filter(it -> it.getIdTaiKhoan() == null).map(it -> giangVienConverter.giangVienToGiangVienDTO(it)).collect(Collectors.toList());
    }
}
