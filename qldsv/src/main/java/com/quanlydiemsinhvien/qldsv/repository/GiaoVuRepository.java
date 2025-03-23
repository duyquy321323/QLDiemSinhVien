/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlydiemsinhvien.qldsv.pojo.Giaovu;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;

/**
 *
 * @author FPTSHOP
 */
public interface GiaoVuRepository extends JpaRepository<Giaovu, Integer> {
    public Optional<Giaovu> findByIdTaiKhoan(Taikhoan idTaiKhoan);
}
