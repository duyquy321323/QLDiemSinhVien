/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlydiemsinhvien.qldsv.converter.MonHocDangKyConverter;
import com.quanlydiemsinhvien.qldsv.converter.SinhVienConverter;
import com.quanlydiemsinhvien.qldsv.dto.SinhVienDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Diem;
import com.quanlydiemsinhvien.qldsv.pojo.DiemMonHoc;
import com.quanlydiemsinhvien.qldsv.pojo.MonhocHocky;
import com.quanlydiemsinhvien.qldsv.pojo.Monhocdangky;
import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;
import com.quanlydiemsinhvien.qldsv.repository.DiemRepository;
import com.quanlydiemsinhvien.qldsv.repository.MonhocHockyRepository;
import com.quanlydiemsinhvien.qldsv.repository.MonhocdangkyRepository;
import com.quanlydiemsinhvien.qldsv.repository.SinhVienRepository;
import com.quanlydiemsinhvien.qldsv.service.KeycloakUserService;
import com.quanlydiemsinhvien.qldsv.service.SinhVienService;

/**
 *
 * @author Admin
 */
@Service
@Transactional
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhvienRepository;
    
    @Autowired
    private  DiemRepository diemRepository;
    
    @Autowired
    private MonhocHockyRepository monhocHockyRepository;
    @Autowired
    private MonhocdangkyRepository monhocdangkyRepository;

    @Autowired
    private KeycloakUserService keycloakUserService;

    @Autowired
    private MonHocDangKyConverter monHocDangKyConverter;

    @Autowired
    private SinhVienConverter sinhVienConverter;

    @Override
    public SinhVienDTO getSinhvien(String idTaiKhoan) {
        return sinhVienConverter.sinhVienToSinhVienDTO(sinhvienRepository.findByIdTaiKhoan(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản này không phải là sinh viên!")));
    }

    @Override
    public Page<SinhVienDTO> getSinhvienList(Map<String, String> params, int page, int pageSize) {
        try{
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            String tenSV = params.get("tensv");

            if(tenSV != null){
                Page<Sinhvien> sinhVienList = sinhvienRepository.findByHoTenContaining(tenSV, pageable);
                return sinhVienList.map(it -> it != null ? sinhVienConverter.sinhVienToSinhVienDTO(it) : null);
            }
            Page<Sinhvien> sinhVienList = sinhvienRepository.findAll(pageable);
            return sinhVienList.map(it -> it != null ? sinhVienConverter.sinhVienToSinhVienDTO(it) : null);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    // di chuyen lay danh sach lop hoc qua class lop hoc
    @Override
    public boolean addOrUpdateSinhVien(SinhVienDTO sv) {
        try {
            Sinhvien sinhvien = sv.getIdSinhVien() == null? null : sinhvienRepository.findById(sv.getIdSinhVien()).orElse(null);
            if(sinhvien != null && sinhvien.getIdTaiKhoan() != null){
                keycloakUserService.updateUser(sinhvien.getIdTaiKhoan(), sv.getEmail(), sv.getHoTen());
            }
            sinhvienRepository.save(sinhVienConverter.sinhVienDTOToSinhVien(sv));
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SinhVienDTO getSinhVienById(Integer idSinhVien) {
        return sinhVienConverter.sinhVienToSinhVienDTO(sinhvienRepository.findById(idSinhVien).orElse(null));
    }

//    xoa sinh vien voi khoa ngoai
    @Override
    public boolean deleteById(Integer idSinhVien) {
        try{
            Sinhvien sinhvien = sinhvienRepository.findById(idSinhVien).orElse(null);
            if(sinhvien != null && sinhvien.getIdTaiKhoan() != null){
                keycloakUserService.deleteUser(sinhvien.getIdTaiKhoan());
            }
            sinhvienRepository.deleteById(idSinhVien);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<DiemMonHoc> getSinhvienByMonHoc(Map<String, String> params) {
        String idMonHoc = params.get("monHocId");
        String tenSinhVien = params.get("tenSinhVien");
        MonhocHocky monhocHocky = monhocHockyRepository.findById(Integer.parseInt(idMonHoc)).orElseThrow(() -> new RuntimeException("Môn học học kỳ không tồn tại!"));
        List<Monhocdangky> monhocdangkyList = new ArrayList<>();
        if(tenSinhVien != null){
            Sinhvien sinhvien = sinhvienRepository.findByHoTen(tenSinhVien).orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại!"));
            Monhocdangky monhocdangky = monhocdangkyRepository.findByIdSinhVienAndIdMonHoc(sinhvien, monhocHocky).orElseThrow(() -> new RuntimeException("Môn học đăng ký không tồn tại!"));
            monhocdangkyList.add(monhocdangky);
        } else {
            monhocdangkyList.addAll(monhocdangkyRepository.findByIdMonHoc(monhocHocky));
        }

        List<DiemMonHoc> monHocDiemList = new ArrayList<>();

       for (Monhocdangky monHoc : monhocdangkyList) {
            DiemMonHoc monHocDiem = DiemMonHoc.fromMonHocDangKy(monHocDangKyConverter.monhocdangkyToMonhocdangkyDTO(monHoc));
            // Lấy danh sách điểm cho môn học cụ thể
            List<Diem> diemList = diemRepository.findByIdMonHoc(monHoc);
            // Thêm điểm vào danh sách MonHocDiem
            for (Diem diem : diemList) {
                monHocDiem.addDiem(diem); // Diem.getDiem() là phương thức lấy giá trị điểm
            }
            // Thêm MonHocDiem vào danh sách chung
            monHocDiemList.add(monHocDiem);
       }
        return monHocDiemList;
    }
    
    // dem sv
    @Override
    public Long countSinhVien() {
        return this.sinhvienRepository.count();
    }

    //update 26/9 danh sach sinh vien theo ma lop
    @Override
    public List<SinhVienDTO> getSinhVienByIdLop(int idLop) {
        try {
            return sinhvienRepository.findByMaLop_IdLopHoc(idLop).stream().map(it -> sinhVienConverter.sinhVienToSinhVienDTO(it)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getSinhVienByIdAPI(Integer idSinhVien) {
        try {
            Sinhvien sinhvien = sinhvienRepository.findById(idSinhVien).orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại!"));
            return new Object[]{sinhvien.getHoTen(), sinhvien.getIdSinhVien(), sinhvien.getNgaySinh(), sinhvien.getMaLop().getTenLopHoc(), sinhvien.getMaLop().getIdNganh().getTenNganhDaoTao(), sinhvien.getDiaChi(), sinhvien.getEmail()};
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
