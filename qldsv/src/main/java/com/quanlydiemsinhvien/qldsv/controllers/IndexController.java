 /*
  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
  */
 package com.quanlydiemsinhvien.qldsv.controllers;


 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.RequestMapping;

 @Controller
 @ControllerAdvice
 public class IndexController {

     @RequestMapping("/")
     public String index() {
         return "pages/access/index";
     }
 }
