/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Lophoc;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author FPTSHOP
 */
public class LopHocFormatter implements Formatter<Lophoc>{

    @Override
    public String print(Lophoc lh, Locale locale) {
        return String.valueOf(lh.getIdLopHoc());
    }

    @Override
    public Lophoc parse(String lhID, Locale locale) throws ParseException {
        return Lophoc.builder().idLopHoc(Integer.parseInt(lhID)).build();
    }
    
}
