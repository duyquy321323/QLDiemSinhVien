/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;

/**
 *
 * @author Admin
 */
public interface SinhVienRepository extends JpaRepository<Sinhvien, Integer> {
    public Optional<Sinhvien> findByHoTen(String hoTen);
    public Optional<Sinhvien> findByIdTaiKhoan(String idTaiKhoan);
    public Optional<Sinhvien> findByEmail(String email);
    List<Sinhvien> findByMaLop_IdLopHoc(Integer idLop);
    Page<Sinhvien> findByHoTenContaining(String hoTen, Pageable pageable);
    public Long countByMaLop_IdLopHoc(Integer idLopHoc);
}
