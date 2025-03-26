/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.controllers;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
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

import com.quanlydiemsinhvien.qldsv.converter.GiangVienConverter;
import com.quanlydiemsinhvien.qldsv.dto.GiangVienDTO;
import com.quanlydiemsinhvien.qldsv.request.GiangvienCreateRequest;
import com.quanlydiemsinhvien.qldsv.service.GiangVienService;


@RestController
public class GiangVienController {

    @Autowired
    private GiangVienService gvService;

    @Autowired
    private GiangVienConverter giangVienConverter;

    @GetMapping("/giaovu/giangvien")
    public Page<GiangVienDTO> list(@RequestParam Map<String, String> params, @RequestParam(defaultValue="1" ,required = false) Integer page,
    @RequestParam(defaultValue = "100", required=false) int pageSize) {
        return gvService.getGiangvienList(params, page, pageSize);
    }

    @GetMapping("/giaovu/giangvienchuacotaikhoan")
    public List<GiangVienDTO> listCCTK() {
        return gvService.getGiangvienCCTKList();
    }

    @GetMapping("/giaovu/giangvien/{id}")
    public GiangVienDTO getGVById(@PathVariable(value = "id") Integer id) {
        return gvService.getGiangVienById(id);
    }

    @PostMapping("/giaovu/giangvien/add")
    public ResponseEntity<?> add(@RequestBody GiangvienCreateRequest gv, Principal principal) throws UnsupportedEncodingException {
        if(gvService.addOrUpdateGiangVien(giangVienConverter.giangVienCreateRequestToGiangVien(gv), principal)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
