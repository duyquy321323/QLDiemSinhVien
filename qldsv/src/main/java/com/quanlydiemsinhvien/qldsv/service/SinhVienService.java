/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.quanlydiemsinhvien.qldsv.dto.SinhVienDTO;
import com.quanlydiemsinhvien.qldsv.pojo.DiemMonHoc;

/**
 *
 * @author Admin
 */
public interface SinhVienService {
    SinhVienDTO getSinhvien(String idTaiKhoan);
    Page<SinhVienDTO> getSinhvienList(Map<String, String> params, int page, int pageSize);
    boolean addOrUpdateSinhVien(SinhVienDTO sv );
    SinhVienDTO getSinhVienById(Integer idSinhVien);
    boolean deleteById(Integer id);
    List<DiemMonHoc> getSinhvienByMonHoc(Map<String, String> params);
    Long countSinhVien();
    List<SinhVienDTO> getSinhVienByIdLop(int idLop);
    Object getSinhVienByIdAPI(Integer idSinhVien);
}
