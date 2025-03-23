/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class CauHoiFormatter implements Formatter<Cauhoidiendang>{

    @Override
    public String print(Cauhoidiendang cauhoi, Locale locale) {
        return String.valueOf(cauhoi.getIdCauHoiDienDan());
    }

    @Override
    public Cauhoidiendang parse(String cauhoiId, Locale locale) throws ParseException {
       return Cauhoidiendang.builder().idCauHoiDienDan(Integer.parseInt(cauhoiId)).build();
    }
    
}
