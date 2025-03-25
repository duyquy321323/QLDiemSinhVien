/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quanlydiemsinhvien.qldsv.dto.GiangVienDTO;
import com.quanlydiemsinhvien.qldsv.dto.SinhVienDTO;
import com.quanlydiemsinhvien.qldsv.dto.TaiKhoanDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import com.quanlydiemsinhvien.qldsv.request.LoginRequest;
import com.quanlydiemsinhvien.qldsv.service.GiangVienService;
import com.quanlydiemsinhvien.qldsv.service.SinhVienService;
import com.quanlydiemsinhvien.qldsv.service.TaiKhoanService;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private TaiKhoanService tkService;
    @Autowired
    private GiangVienService gvService;
    @Autowired
    private SinhVienService sinhvienService;

    @PostMapping("/login/")
    @CrossOrigin
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tkService.login(user));
    }

    @PostMapping(path = "/users/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<String> addUser(@RequestParam Map<String, String> params) {
        if (this.tkService.createTKSinhVien(params)) {
            return new ResponseEntity<>("Successfull", HttpStatus.OK);
        }
        return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/change-password/")
    @CrossOrigin
    public ResponseEntity<?> changePassword(@RequestParam Map<String, String> params) {
            return tkService.thayDoiMatKhau(params);
    }

    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<TaiKhoanDTO> details(Principal user) {
        try {
            TaiKhoanDTO u = this.tkService.getUserById(user.getName());
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/current-sinhvien/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<SinhVienDTO> detailsinhven(Principal user) {
        TaiKhoanDTO u = this.tkService.getUserById(user.getName());
        SinhVienDTO sv = this.sinhvienService.getSinhvien(u.getIdTaiKhoan());
        return new ResponseEntity<>(sv, HttpStatus.OK);
    }

    @PostMapping(path = "/change-avt/", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Taikhoan> updateAVT(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        Taikhoan user = this.tkService.updateImg(params, avatar);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path = "/current-user-gv/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<GiangVienDTO> detailsgiangvien(Principal user) {
        TaiKhoanDTO u = this.tkService.getUserById(user.getName());
        GiangVienDTO gv = this.gvService.getGiangVienByIdTaiKhoan(u.getIdTaiKhoan());
        return new ResponseEntity<>(gv, HttpStatus.OK);
    }

    @PostMapping("/send-code/")
    @CrossOrigin
    public ResponseEntity<String> sendCode(@RequestParam Map<String, String> params) {
        if (this.tkService.sendCode(params.get("email"))) {
            return new ResponseEntity<>("sucessful", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.CREATED);
    }

    @GetMapping("/SinhVien-Id/")
    @CrossOrigin
    public ResponseEntity<Object> getSVBYID(@RequestParam Map<String, String> params) {
        String idSinhVien = params.get("idSinhVien");

        return new ResponseEntity<>(this.sinhvienService.getSinhVienByIdAPI(Integer.valueOf(idSinhVien)), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Logout-Auth") String authHeader) {
        return tkService.logout(authHeader);
    }
}
