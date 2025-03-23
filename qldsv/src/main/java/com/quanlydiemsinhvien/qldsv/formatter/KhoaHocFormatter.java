/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang;
import com.quanlydiemsinhvien.qldsv.pojo.Khoa;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author FPTSHOP
 */
public class KhoaHocFormatter implements Formatter<Khoa>{

    @Override
    public String print(Khoa khoa, Locale locale) {
        return String.valueOf(khoa.getIdkhoa());
    }

    @Override
    public Khoa parse(String khoaId, Locale locale) throws ParseException {
        return Khoa.builder().idkhoa(Integer.parseInt(khoaId)).build();

    }
    
}
