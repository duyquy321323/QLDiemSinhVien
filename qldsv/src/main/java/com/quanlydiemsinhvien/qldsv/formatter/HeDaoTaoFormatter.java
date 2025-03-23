/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang;
import com.quanlydiemsinhvien.qldsv.pojo.Hedaotao;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author FPTSHOP
 */
public class HeDaoTaoFormatter implements Formatter<Hedaotao>{

    @Override
    public String print(Hedaotao heDT, Locale locale) {
        return String.valueOf(heDT.getIdhedaotao());

    }

    @Override
    public Hedaotao parse(String hdtId, Locale locale) throws ParseException {
       return Hedaotao.builder().idhedaotao(Integer.parseInt(hdtId)).build();
    }
    
}
