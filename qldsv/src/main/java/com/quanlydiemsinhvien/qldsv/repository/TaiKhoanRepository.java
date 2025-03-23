/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;

/**
 *
 * @author Admin
 */
public interface TaiKhoanRepository extends JpaRepository<Taikhoan, String> {
    public Optional<Taikhoan> findByTenTaiKhoan(String tenTaiKhoan);
    Page<Taikhoan> findByTenTaiKhoanContaining(String tenTK, PageRequest pageRequest);
    Long countByTenTaiKhoan(String tenTaiKhoan);
}
