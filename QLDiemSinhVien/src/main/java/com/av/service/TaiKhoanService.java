/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.av.service;

import com.av.pojo.Loaitaikhoan;
import com.av.pojo.Sinhvien;
import com.av.pojo.Taikhoan;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Admin
 */
public interface TaiKhoanService extends UserDetailsService {
   Taikhoan updateImg(Map<String, String> params, MultipartFile avatar);  
   Taikhoan getTaiKhoan(String idTaiKhoan);
   boolean addAcount(Taikhoan t);
    boolean addAcountGV(Taikhoan t);
    List<Taikhoan> getTaiKhoans(Map<String, String> params);
    Loaitaikhoan getChucVu(int id);
    Taikhoan getUserByUsername(String username);
    UserDetails getLoggedInUserDetails(Authentication authentication);
    boolean addUser(Map<String, String> params);
    String GetIdTaiKhoan(UserDetails userDetails);
    Taikhoan thayDoiMatKhau(Map<String, String> params);
    Taikhoan thayDoiMatKhauAD(Taikhoan user);
    public String login(Taikhoan user);
    boolean authUser(String username, String password);
    Taikhoan getUserById(String id);
    boolean sendCode(String email);

    List<Loaitaikhoan> getLoaitaikhoans(Map<String, String> params);
    Loaitaikhoan getLoaiTaiKhoanById(int id);
    boolean addOrUpdateLoaiTK(Loaitaikhoan ltk);
    long countTaiKhoan();
    long countLoaiTK();

}

