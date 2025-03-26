/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanlydiemsinhvien.qldsv.dto.GiaoVuDTO;
import com.quanlydiemsinhvien.qldsv.request.TaiKhoanGiangVienRequest;
import com.quanlydiemsinhvien.qldsv.service.GiaoVuService;
import com.quanlydiemsinhvien.qldsv.service.TaiKhoanService;

/**
 *
 * @author FPTSHOP
 */
@RestController
public class AdminController {

    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private GiaoVuService giaoVuService;

    @GetMapping("/giaovu/current-giaovu")
    public ResponseEntity<GiaoVuDTO> nameGVu(Principal auth) {
        return ResponseEntity.ok(giaoVuService.getGiaoVu(auth));
    }

    @RequestMapping("/giaovu")
    public String admin() {
        return "admin";
    }

    @GetMapping("/giaovu/thongtin")
    public String thongtin(Model model) {
        return "thongtintaikhoan";
    }

    @GetMapping("giaovu/taikhoan")
    public List<Map<String, Object>> taikhoan(@RequestParam Map<String, String> params) {
        return taiKhoanService.getTaiKhoans(params);
    }

    @PostMapping("/giaovu/taikhoan/dangki")
    public ResponseEntity<?> add(@RequestBody TaiKhoanGiangVienRequest t) {
            if (t.getMatKhau().equals(t.getXacNhanMk())) {
                if (taiKhoanService.addAcountGV(t) == true) {
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.internalServerError().build();
                }
            } else {
                return ResponseEntity.ok().build();
            }
    }

}
