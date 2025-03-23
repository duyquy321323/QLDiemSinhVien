/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service;

import java.util.List;
import java.util.Map;

import com.quanlydiemsinhvien.qldsv.dto.CauhoidiendangDTO;
import com.quanlydiemsinhvien.qldsv.dto.TraloidiendanDTO;
import com.quanlydiemsinhvien.qldsv.request.CauhoidiendangRequest;
import com.quanlydiemsinhvien.qldsv.request.TraloidiendanRequest;

/**
 *
 * @author Admin
 */
public interface DienDanService {
    Object getCauHoi(Map<String, String> params);
    boolean addOrUpdateTraloi(TraloidiendanRequest p);
    boolean addOrUpdateCauHoi(CauhoidiendangRequest p);
    List<CauhoidiendangDTO> getCauHoiDienDan();
    List<TraloidiendanDTO> getTraloi(Map<String, String> params);
    boolean deleteCauHoi(Map<String, String> params);

}
