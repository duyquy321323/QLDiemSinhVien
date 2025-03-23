/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang;
import com.quanlydiemsinhvien.qldsv.pojo.Nganhdaotao;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author FPTSHOP
 */
public class NghanhDaoTaoFormatter implements Formatter<Nganhdaotao>{

    @Override
    public String print(Nganhdaotao ndt, Locale locale) {
        return String.valueOf(ndt.getIdNganhDaoTao());    
    }

    @Override
    public Nganhdaotao parse(String ndtId, Locale locale) throws ParseException {
        return Nganhdaotao.builder().idNganhDaoTao(Integer.parseInt(ndtId)).build();
    }
    
}
