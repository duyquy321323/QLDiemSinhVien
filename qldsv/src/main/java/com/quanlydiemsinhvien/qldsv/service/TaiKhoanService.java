/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.quanlydiemsinhvien.qldsv.dto.TaiKhoanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Loaitaikhoan;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import com.quanlydiemsinhvien.qldsv.request.TaiKhoanGiangVienRequest;
import com.quanlydiemsinhvien.qldsv.request.TaikhoanCreateRequest;

/**
 *
 * @author Admin
 */
public interface TaiKhoanService extends UserDetailsService {
   Taikhoan updateImg(Map<String, String> params, MultipartFile avatar);  
   boolean addAcount(Taikhoan t);
    boolean addAcountGV(TaiKhoanGiangVienRequest t);
    List<Taikhoan> getTaiKhoans(Map<String, String> params);
    Taikhoan getUserByUsername(String username);
    UserDetails getLoggedInUserDetails(Authentication authentication);
    boolean createTKSinhVien(Map<String, String> params);
    String GetIdTaiKhoan(UserDetails userDetails);
    Taikhoan thayDoiMatKhau(Map<String, String> params);
    void thayDoiMatKhauAD(TaikhoanCreateRequest user);
    public Map<String, String> login(Taikhoan user);
    boolean authUser(String username, String password);
    TaiKhoanDTO getUserById(String id);
    boolean sendCode(String email);

    List<Loaitaikhoan> getLoaitaikhoanList(Map<String, String> params);
    Loaitaikhoan getLoaiTaiKhoanById(int id);
    boolean addOrUpdateLoaiTK(Loaitaikhoan ltk);
    long countTaiKhoan();
    ResponseEntity<String> logout(String authHeader);
}

