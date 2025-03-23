/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service.impl;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlydiemsinhvien.qldsv.converter.GiaoVuConverter;
import com.quanlydiemsinhvien.qldsv.dto.GiaoVuDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import com.quanlydiemsinhvien.qldsv.repository.GiaoVuRepository;
import com.quanlydiemsinhvien.qldsv.repository.TaiKhoanRepository;
import com.quanlydiemsinhvien.qldsv.service.GiaoVuService;

/**
 *
 * @author FPTSHOP
 */
@Service
@Transactional
public class GiaoVuServiceImpl implements GiaoVuService{

    @Autowired
    private GiaoVuRepository giaoVuRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private GiaoVuConverter giaoVuConverter;

    @Override
    public GiaoVuDTO getGiaoVu(Principal auth) {
        String id = auth.getName();
        Taikhoan idTaiKhoan = taiKhoanRepository.findById(id).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
        return giaoVuConverter.giaoVuToGiaoVuDTO(giaoVuRepository.findByIdTaiKhoan(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản này không phải là giáo vụ")));
    }
    
}
