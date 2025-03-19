/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.av.controllers;

import com.av.pojo.Khoadaotao;
import com.av.repository.KhoaDaoTaoRepository;
import com.av.service.GiaoVuService;
import com.av.service.KhoaDaoTaoService;
import com.av.service.TaiKhoanService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import com.av.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author FPTSHOP
 */
@Controller
@PropertySource("classpath:configs.properties")
public class KhoaDaoTaoController {

    @Autowired
    private KhoaDaoTaoService khoaDTService;

    @Autowired
    private TaiKhoanService tkService;

    @Autowired
    private GiaoVuService gvuService;

    @Autowired
    private Environment env;

    @RequestMapping("/giaovu/khoadaotao")
    public String khoaDT(Authentication auth, Model model, @RequestParam Map<String, String> params, HttpSession session) {
        model.addAttribute("khoadt", this.khoaDTService.getKhoadaotaos(params));
        model.addAttribute("giaovu", this.gvuService.getGiaovus(this.tkService.GetIdTaiKhoan(tkService.getLoggedInUserDetails(auth))));

        // phan trang
        int page = 1;
        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        long count = this.khoaDTService.countKhoaDaoTao();

        if (params.containsKey("pageKDT")) {
            page = Integer.parseInt(params.get("pageKDT"));
            // Lưu trang hiện tại vào session
            session.setAttribute("currentPage", page);
        } else {
            // Nếu không có tham số trang trong request, thử lấy trang từ session (nếu có)
            Integer currentPageFromSession = (Integer) session.getAttribute("currentPage");
            if (currentPageFromSession != null) {
                page = currentPageFromSession;
            }
        }

        // Lấy danh sách khoa dao tao cho trang hiện tại
        List<Khoadaotao> kdtList = this.khoaDTService.getKhoadaotaos(params);

        // Tính số trang
        int totalRecords = kdtList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (kdtList.isEmpty()) {
            page = 1;
        } else {
            if (page < 1) {
                page = 1;
            }
            if (page > totalPages) {
                page = totalPages;
            }
        }

        // Trích xuất danh sách khoa đào tạo cho trang hiện tại
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalRecords);

        List<Khoadaotao> mhsForCurrentPage = kdtList.subList(start, end);
        model.addAttribute("khoadt", mhsForCurrentPage);

        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        return "khoadaotao";
    }

    @GetMapping("/giaovu/khoadaotao/add")
    public String addKDT(Authentication auth, Model model) {
        model.addAttribute("giaovu", this.gvuService.getGiaovus(this.tkService.GetIdTaiKhoan(tkService.getLoggedInUserDetails(auth))));

        model.addAttribute("khoadaotao", new Khoadaotao());
        return "addkhoadt";
    }

    @GetMapping("/giaovu/khoadaotao/add/{id}")
    public String update(Authentication auth, Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("giaovu", this.gvuService.getGiaovus(this.tkService.GetIdTaiKhoan(tkService.getLoggedInUserDetails(auth))));

        model.addAttribute("khoadaotao", this.khoaDTService.getKhoadaotaoById(id));
        return "addkhoadt";
    }

    @PostMapping("/giaovu/khoadaotao/add")
    public String add(@ModelAttribute(value = "khoadaotao") Khoadaotao kdt) {
        kdt.setTenKhoaDaoTao(Utils.decodeBase64(kdt.getTenKhoaDaoTao()));
        if (this.khoaDTService.addOrUpdateKhoaDT(kdt) == true) {
            return "redirect:/giaovu/khoadaotao";
        }
        return "addkhoadt";
    }
}
