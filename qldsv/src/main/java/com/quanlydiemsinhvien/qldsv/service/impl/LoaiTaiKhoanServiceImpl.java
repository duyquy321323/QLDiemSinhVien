package com.quanlydiemsinhvien.qldsv.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlydiemsinhvien.qldsv.converter.LoaitaikhoanConverter;
import com.quanlydiemsinhvien.qldsv.dto.LoaitaikhoanDTO;
import com.quanlydiemsinhvien.qldsv.repository.LoaitaikhoanRepository;
import com.quanlydiemsinhvien.qldsv.service.LoaiTaiKhoanService;

@Service
@Transactional
public class LoaiTaiKhoanServiceImpl implements LoaiTaiKhoanService {

    @Autowired
    private LoaitaikhoanRepository loaitaikhoanRepository;

    @Autowired
    private LoaitaikhoanConverter loaitaikhoanConverter;

    @Override
    public List<LoaitaikhoanDTO> getLoaiTaiKhoanList() {
        return loaitaikhoanRepository.findAll().stream().map(it -> loaitaikhoanConverter.loaitaikhoanToLoaitaikhoanDTO(it)).collect(Collectors.toList());
    }
}