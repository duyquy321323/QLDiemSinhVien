/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Hocky;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author FPTSHOP
 */
public class HocKiFormatter implements Formatter<Hocky> {

    @Override
    public String print(Hocky hk, Locale locale) {
        return String.valueOf(hk.getIdHocKy());
    }

    @Override
    public Hocky parse(String hkId, Locale locale) throws ParseException {
        return Hocky.builder().idHocKy(Integer.parseInt(hkId)).build();
    }
}
