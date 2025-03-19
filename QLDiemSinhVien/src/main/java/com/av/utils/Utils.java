/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.av.utils;

import com.av.pojo.PhieuMonHoc;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 *
 * @author FPTSHOP
 */
public class Utils {
    public static int countCart(Map<Integer, PhieuMonHoc> cart){
        int count = 0;
        if(cart != null){
            for(PhieuMonHoc c: cart.values()){
                count += c.getDem();
            }
        }
        return count;
    }

    public static String decodeBase64(String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }
    
}
