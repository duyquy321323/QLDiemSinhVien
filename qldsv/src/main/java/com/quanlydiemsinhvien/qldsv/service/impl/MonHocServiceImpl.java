/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlydiemsinhvien.qldsv.converter.MonHocConverter;
import com.quanlydiemsinhvien.qldsv.converter.MonHocDangKyConverter;
import com.quanlydiemsinhvien.qldsv.converter.MonHocHocKyConverter;
import com.quanlydiemsinhvien.qldsv.dto.MonhocDTO;
import com.quanlydiemsinhvien.qldsv.dto.MonhocHockyDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Giangvien;
import com.quanlydiemsinhvien.qldsv.pojo.Monhoc;
import com.quanlydiemsinhvien.qldsv.pojo.MonhocHocky;
import com.quanlydiemsinhvien.qldsv.pojo.Monhocdangky;
import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import com.quanlydiemsinhvien.qldsv.repository.GiangvienRepository;
import com.quanlydiemsinhvien.qldsv.repository.MonHocRepository;
import com.quanlydiemsinhvien.qldsv.repository.MonhocHockyRepository;
import com.quanlydiemsinhvien.qldsv.repository.MonhocdangkyRepository;
import com.quanlydiemsinhvien.qldsv.repository.SinhVienRepository;
import com.quanlydiemsinhvien.qldsv.repository.TaiKhoanRepository;
import com.quanlydiemsinhvien.qldsv.service.MonHocService;

/**
 *
 * @author FPTSHOP
 */
@Service
@Transactional
public class MonHocServiceImpl  implements MonHocService{
    
    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private MonhocHockyRepository monhocHocKyRepository;
    @Autowired
    private GiangvienRepository giangvienRepository;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private MonhocdangkyRepository monhocdangkyRepository;

    @Autowired
    private MonHocConverter monHocConverter;

    @Autowired
    private MonHocHocKyConverter monHocHocKyConverter;

    @Autowired
    private MonHocDangKyConverter monHocDangKyConverter;

    @Override
    public Page<MonhocDTO> getMonHocList(Map<String, String> params, int page, int pageSize) {
        try {
            String ten = params.get("tenMH");
            Pageable pageable = PageRequest.of(page - 1, pageSize);
            if(ten != null){
                Page<Monhoc> monHocList = monHocRepository.findByTenMonHocContaining(ten, pageable);
                return monHocList.map(it -> it != null ? monHocConverter.monhocToMonhocDTO(it) : null);
            }
            Page<Monhoc> monHocList = monHocRepository.findAll(pageable);
            return monHocList.map(it -> it != null ? monHocConverter.monhocToMonhocDTO(it) : null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addOrUpdateMonHoc(Monhoc mh) {
        try{
            monHocRepository.save(mh);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MonhocDTO getMonHocById(int id) {
        try{
            return monHocConverter.monhocToMonhocDTO(monHocRepository.findById(id).orElseThrow(() -> new RuntimeException("Môn học không tồn tại!")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteMonHoc(int id) {
        try{
            monHocRepository.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Monhoc getMonhocByIdMonhocHocKy(Integer idMonHocHocKy) {
        MonhocHocky monhocHocky = monhocHocKyRepository.findById(idMonHocHocKy).orElseThrow(() -> new RuntimeException("Môn học học kỳ không tồn tại!"));
        return monhocHocky.getIdMonHoc();
    }

    @Override
    public List<MonhocHockyDTO> getMonHocByGiangVien(Map<String, String> params) {
        String idTaiKhoan = params.get("taiKhoanId");
        Taikhoan taikhoan = taiKhoanRepository.findById(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
        Giangvien giangvien = giangvienRepository.findByIdTaiKhoan(taikhoan).orElseThrow(() -> new RuntimeException("Tài khoản không phải là giáo viên!"));
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return monhocHocKyRepository.findByIdGiangVienAndIdHocky_NgayBatDauLessThanEqualAndIdHocky_NgayKetThucGreaterThanEqual(giangvien, currentDate, currentDate)
                .stream().map(it -> monHocHocKyConverter.monhocHockyToMonhocHockyDTO(it)).collect(Collectors.toList());
    }

    @Override
    public List<Monhoc> getMonHocByIdSinhVien(String idSinhvien) {
        List<Monhocdangky> monhocdangkyList = monhocdangkyRepository.findByIdSinhVien_IdSinhVienAndTrangThaiAndKhoaMon(idSinhvien, (short)1, (short)1);
        Set<Monhoc> monhocHockyList = new HashSet<>();
        for(Monhocdangky monhocdangky : monhocdangkyList){
            monhocHockyList.add(monhocdangky.getIdMonHoc().getIdMonHoc());
        }
        return new ArrayList<>(monhocHockyList);
    }

    @Override
    public List<Monhoc> getMonHocByIdSinhVienDangHoc(String idSinhvien) {
        List<Monhocdangky> monhocdangkyList = monhocdangkyRepository.findByIdSinhVien_IdSinhVienAndTrangThaiAndKhoaMon(idSinhvien, (short)1, (short)0);
        Set<Monhoc> monhocHockyList = new HashSet<>();
        for(Monhocdangky monhocdangky : monhocdangkyList){
            monhocHockyList.add(monhocdangky.getIdMonHoc().getIdMonHoc());
        }
        return new ArrayList<>(monhocHockyList);
    }

    @Override
    public List<MonhocHocky> getMonHocHocKy(Map<String, String> params) {
        String tenMonHoc = params.get("tenMonHoc");
        String idTaiKhoan = params.get("idTaiKhoan");
        Taikhoan taikhoan = taiKhoanRepository.findById(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
        Sinhvien sinhvien = sinhVienRepository.findByIdTaiKhoan(taikhoan).orElseThrow(() -> new RuntimeException("Tài khoản này không phải là sinh viên!"));
        String idLopHoc = sinhvien.getMaLop().getIdLopHoc().toString();
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(tenMonHoc != null){
            return monhocHocKyRepository.findByIdHocky_IdLop_IdLopHocAndIdMonHoc_TenMonHocAndIdHocky_NgayDangKyLessThanEqualAndIdHocky_NgayHetHanGreaterThanEqual(Integer.parseInt(idLopHoc), tenMonHoc, currentDate, currentDate);
        } else {
            return monhocHocKyRepository.findByIdHocky_IdLop_IdLopHocAndIdHocky_NgayDangKyLessThanEqualAndIdHocky_NgayHetHanGreaterThanEqual(Integer.parseInt(idLopHoc), currentDate, currentDate);
        }
    }

    @Override
    public List<Monhocdangky> getMonHocSinhVienDangKy(Map<String, String> params) {
        String idSinhVien = params.get("idSinhVien");
        Sinhvien sinhvien = sinhVienRepository.findById(Integer.valueOf(idSinhVien)).orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại!"));
        List<MonhocHocky> monhocHockyList = monhocHocKyRepository.findAll();
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return monhocdangkyRepository.findByIdMonHocInAndIdSinhVienAndIdMonHoc_IdHocky_NgayDangKyLessThanEqualAndIdMonHoc_IdHocky_NgayHetHanGreaterThanEqual(monhocHockyList, sinhvien, currentDate, currentDate);
    }

    @Override
    public List<MonhocHockyDTO> getMonHocByGiangVienChuaDay(Map<String, String> params) {
        String idTaiKhoan = params.get("taiKhoanId");
        Taikhoan taikhoan = taiKhoanRepository.findById(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
        Giangvien giangvien = giangvienRepository.findByIdTaiKhoan(taikhoan).orElseThrow(() -> new RuntimeException("Tài khoản không phải là giáo viên!"));
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return monhocHocKyRepository.findByIdGiangVienAndIdHocky_NgayBatDauGreaterThan(giangvien, currentDate).stream().map(it -> monHocHocKyConverter.monhocHockyToMonhocHockyDTO(it)).collect(Collectors.toList());
    }

    @Override
    public List<MonhocHockyDTO> getMonHocByGiangVienDaDay(Map<String, String> params) {
        String idTaiKhoan = params.get("taiKhoanId");
        Taikhoan taikhoan = taiKhoanRepository.findById(idTaiKhoan).orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));
        Giangvien giangvien = giangvienRepository.findByIdTaiKhoan(taikhoan).orElseThrow(() -> new RuntimeException("Tài khoản không phải là giáo viên!"));
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        return monhocHocKyRepository.findByIdGiangVienAndIdHocky_NgayKetThucLessThan(giangvien, currentDate).stream().map(it -> monHocHocKyConverter.monhocHockyToMonhocHockyDTO(it)).collect(Collectors.toList());
    }

    @Override
    public boolean thanhToanHocPhi(Map<String, String> params) {
        List<Monhocdangky> monHocs = this.getMonHocSinhVienDangKy(params);
        if (!monHocs.isEmpty()) {
            short thanhToan = 1;
            for (Monhocdangky monhoc : monHocs) {
                monhoc.setThanhToan(thanhToan);
            }
            monhocdangkyRepository.saveAll(monHocs);
            return true;
        }
        return false;
    }
   
    @Override
    public Long countMonHoc() {
        return this.monHocRepository.count();
    }

    @Override
    public List<MonhocHockyDTO> getMonhocByHocKy(Integer idHocKy) {
        try {
            return monhocHocKyRepository.findByIdHocky_IdHocKy(idHocKy).stream().map(it -> monHocHocKyConverter.monhocHockyToMonhocHockyDTO(it)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
