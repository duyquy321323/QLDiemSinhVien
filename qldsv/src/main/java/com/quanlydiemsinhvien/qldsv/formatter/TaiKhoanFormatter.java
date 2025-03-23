/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.formatter;

import com.quanlydiemsinhvien.qldsv.pojo.Taikhoan;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Admin
 */
public class TaiKhoanFormatter implements Formatter<Taikhoan>{

    @Override
    public String print(Taikhoan taikhoan, Locale locale) {
        return String.valueOf(taikhoan.getIdTaiKhoan());
    }

    @Override
    public Taikhoan parse(String taikhoanId, Locale locale) throws ParseException {
       return Taikhoan.builder().idTaiKhoan(taikhoanId).build();
    }
    
}
