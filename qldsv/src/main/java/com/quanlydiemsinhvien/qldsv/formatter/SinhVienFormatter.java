/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author FPTSHOP
 */
public class SinhVienFormatter implements Formatter<Sinhvien>{

    @Override
    public String print(Sinhvien sv, Locale locale) {
        return String.valueOf(sv.getIdSinhVien());    
    }

    @Override
    public Sinhvien parse(String text, Locale locale) throws ParseException {
        return Sinhvien.builder().idSinhVien(Integer.valueOf(text)).build();
    }
    
    
}
