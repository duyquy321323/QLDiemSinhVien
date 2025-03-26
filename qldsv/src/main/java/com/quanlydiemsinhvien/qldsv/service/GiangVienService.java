/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.quanlydiemsinhvien.qldsv.dto.GiangVienDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Giangvien;

/**
 *
 * @author FPTSHOP
 */
public interface GiangVienService {
    Page<GiangVienDTO> getGiangvienList(Map<String, String> params, int page, int pageSize);
    boolean addOrUpdateGiangVien(Giangvien gv, Principal principal);
    GiangVienDTO getGiangVienById(Integer idGiangVien);
    boolean deleteById(Integer idGiangVien);
    GiangVienDTO getGiangVienByIdTaiKhoan(String idTaiKhoan);
    List<GiangVienDTO> getGiangvienCCTKList();
    long countGiangVien();
}
