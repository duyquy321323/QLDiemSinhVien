/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quanlydiemsinhvien.qldsv.pojo.Giangvien;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;

/**
 *
 * @author FPTSHOP
 */
public interface GiangvienRepository extends JpaRepository<Giangvien, Integer> {
    Optional<Giangvien> findByIdTaiKhoan(Taikhoan idTaiKhoan);
    Optional<Giangvien> findByIdTaiKhoan_IdTaiKhoan(String idTaiKhoan);
    Page<Giangvien> findByHoTenContaining(String hoTen, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Giangvien g WHERE g.idGiangVien = :id")
    void deleteGiangVienById(@Param("id") Integer id);
}
