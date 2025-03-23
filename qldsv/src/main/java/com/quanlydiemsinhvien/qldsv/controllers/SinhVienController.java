/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanlydiemsinhvien.qldsv.converter.SinhVienConverter;
import com.quanlydiemsinhvien.qldsv.dto.SinhVienDTO;
import com.quanlydiemsinhvien.qldsv.request.SinhVienCreateRequest;
import com.quanlydiemsinhvien.qldsv.service.SinhVienService;

/**
 *
 * @author FPTSHOP
 */
@RestController
public class SinhVienController {

    @Autowired
    private SinhVienService svService;

    @Autowired
    private SinhVienConverter sinhVienConverter;

    @GetMapping("/giaovu/sinhvien")
    public Page<SinhVienDTO> list(@RequestParam Map<String, String> params, @RequestParam(defaultValue="1" ,required = false) Integer page,
    @RequestParam(defaultValue = "100", required=false) int pageSize) {
        return svService.getSinhvienList(params, page, pageSize);
    }

    @PostMapping("/giaovu/sinhvien/add")
    public ResponseEntity<?> add(@RequestBody SinhVienCreateRequest sv) {
            if (this.svService.addOrUpdateSinhVien(sinhVienConverter.sinhVienRequestToSinhVienDTO(sv))) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("/giaovu/sinhvien/{id}")
    public SinhVienDTO chiTietSinhVien(@PathVariable(value = "id") Integer id) {
        return svService.getSinhVienById(id);
    }

    @GetMapping("/giaovu/sinhvien/lophoc/{id}")
    public ResponseEntity<List<SinhVienDTO>> getSinhVienListByLopHoc(@PathVariable(value="id") Integer id){
        return ResponseEntity.ok(svService.getSinhVienByIdLop(id));
    }
}
