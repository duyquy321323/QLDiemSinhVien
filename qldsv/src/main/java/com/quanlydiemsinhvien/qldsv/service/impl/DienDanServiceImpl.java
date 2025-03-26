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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlydiemsinhvien.qldsv.converter.CauhoidiendangConverter;
import com.quanlydiemsinhvien.qldsv.converter.TraloidiendanConverter;
import com.quanlydiemsinhvien.qldsv.dto.CauhoidiendangDTO;
import com.quanlydiemsinhvien.qldsv.dto.TraloidiendanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang;
import com.quanlydiemsinhvien.qldsv.pojo.Traloidiendan;
import com.quanlydiemsinhvien.qldsv.repository.CauhoidiendangRepository;
import com.quanlydiemsinhvien.qldsv.repository.TraloidiendanRepository;
import com.quanlydiemsinhvien.qldsv.request.CauhoidiendangRequest;
import com.quanlydiemsinhvien.qldsv.request.TraloidiendanRequest;
import com.quanlydiemsinhvien.qldsv.service.DienDanService;
import com.quanlydiemsinhvien.qldsv.service.KeycloakUserService;

@Service
@Transactional
public class DienDanServiceImpl implements DienDanService {

    @Autowired
    private TraloidiendanRepository traloidiendanRepository;

    @Autowired
    private CauhoidiendangRepository cauhoidiendangRepository;

    @Autowired
    private CauhoidiendangConverter cauhoidiendangConverter;

    @Autowired
    private TraloidiendanConverter traloidiendanConverter;

    @Autowired
    private KeycloakUserService keycloakUserService;

    @Override
    public Object getCauHoi(Map<String, String> params, Principal principal) {
        String cateId = params.get("cauhoiId");
            Cauhoidiendang cauhoidiendang = cauhoidiendangRepository.findById(Integer.parseInt(cateId))
            .orElseThrow(() -> new RuntimeException("Câu hỏi diễn đàng không tồn tại!"));
        return new Object[] { cauhoidiendang.getNoiDungCauHoi(), cauhoidiendang.getNgayTao(), keycloakUserService.getUsernameByUserId(principal.getName()) };
    }

    @Override
    public boolean addOrUpdateTraloi(TraloidiendanRequest p, Principal principal) {
        if (p.getNoiDungTraLoi() == null) {
            return false;
        } else {
            Cauhoidiendang cauhoidiendang = cauhoidiendangRepository.findById(p.getIdCauHoiDienDan()).orElse(null);
            Traloidiendan traloidiendan = Traloidiendan.builder().idCauHoiDienDan(cauhoidiendang).idTaiKhoan(principal.getName())
                    .noiDungTraLoi(p.getNoiDungTraLoi()).build();
            traloidiendanRepository.save(traloidiendan);
        }
        return true;
    }

    @Override
    public List<CauhoidiendangDTO> getCauHoiDienDan() {
        return cauhoidiendangRepository.findAll().stream()
                .map(it -> cauhoidiendangConverter.cauhoidiendangToCauhoidiendangDTO(it)).collect(Collectors.toList());
        
    }

    @Override
    public List<TraloidiendanDTO> getTraloi(Map<String, String> params) {
        String cateId = params.get("cauhoiId");
        Cauhoidiendang cauhoidiendang = cauhoidiendangRepository.findById(Integer.parseInt(cateId))
                .orElseThrow(() -> new RuntimeException("Câu hỏi không tồn tại!"));
        return traloidiendanRepository.findByIdCauHoiDienDan(cauhoidiendang).stream()
                .map(it -> traloidiendanConverter.traloidiendanToTraloidiendanDTO(it)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCauHoi(Map<String, String> params) {
        try {
            String cateId = params.get("idCauHoiDienDan");
            cauhoidiendangRepository.deleteById(Integer.parseInt(cateId));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addOrUpdateCauHoi(CauhoidiendangRequest p, Principal principal) {
        try {
            if (p.getNoiDungCauHoi() == null) {
                return false;
            } else {
                Cauhoidiendang cauhoidiendang;
                if(p.getIdCauHoiDienDan() != null){
                    cauhoidiendang = cauhoidiendangRepository.findById(p.getIdCauHoiDienDan()).orElse(null);
                    if(cauhoidiendang != null){
                        cauhoidiendang.setNoiDungCauHoi(p.getNoiDungCauHoi());
                        cauhoidiendangRepository.save(cauhoidiendang);
                        return true;
                    } 
                }else {
                    cauhoidiendang = Cauhoidiendang.builder().idTaiKhoan(principal.getName())
                    .noiDungCauHoi(p.getNoiDungCauHoi()).idCauHoiDienDan(p.getIdCauHoiDienDan())
                    .ngayTao(p.getNgayTao()).build();
                    cauhoidiendangRepository.save(cauhoidiendang);
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
