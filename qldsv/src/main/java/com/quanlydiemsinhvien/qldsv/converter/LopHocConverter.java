package com.quanlydiemsinhvien.qldsv.converter;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.quanlydiemsinhvien.qldsv.dto.LopHocDTO;
import com.quanlydiemsinhvien.qldsv.pojo.Hocky;
import com.quanlydiemsinhvien.qldsv.pojo.Lophoc;
import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;
import com.quanlydiemsinhvien.qldsv.repository.HeDaoTaoRepository;
import com.quanlydiemsinhvien.qldsv.repository.KhoaRepository;
import com.quanlydiemsinhvien.qldsv.repository.LopHocRepository;
import com.quanlydiemsinhvien.qldsv.repository.NghanhDaoTaoRepository;
import com.quanlydiemsinhvien.qldsv.repository.SinhVienRepository;
import com.quanlydiemsinhvien.qldsv.request.LophocCreateRequest;

@Component
public class LopHocConverter {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KhoaConverter khoaConverter;

    @Autowired
    private HeDaoTaoConverter heDaoTaoConverter;

    @Autowired
    private NganhDaoTaoConverter nganhDaoTaoConverter;

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private LopHocRepository lopHocRepository;

    @Autowired
    private HeDaoTaoRepository heDaoTaoRepository;

    @Autowired
    private NghanhDaoTaoRepository nghanhDaoTaoRepository;

    @Autowired
    private KhoaRepository khoaRepository;

    public LopHocDTO lopHocToLopHocDTO(Lophoc lophoc){
        LopHocDTO lopHocDTO = modelMapper.map(lophoc, LopHocDTO.class);
        lopHocDTO.setIdKhoaHoc(khoaConverter.khoaToKhoaDTO(lophoc.getIdKhoaHoc()));
        lopHocDTO.setIdHeDaoTao(heDaoTaoConverter.hedaotaoToHedaotaoDTO(lophoc.getIdHeDaoTao()));
        lopHocDTO.setIdNganh(nganhDaoTaoConverter.nganhdaotaoToNganhdaotaoDTO(lophoc.getIdNganh()));
        lopHocDTO.setSiSo(sinhVienRepository.countByMaLop_IdLopHoc(lophoc.getIdLopHoc()));
        return lopHocDTO;
    }

    public Lophoc lopHocCreateRequestToLopHoc(LophocCreateRequest request){
        Lophoc lophoc = request.getIdLopHoc() == null? null : lopHocRepository.findById(request.getIdLopHoc()).orElse(null);
        Set<Hocky> hockySet = new HashSet<>();
        Set<Sinhvien> sinhVienSet = new HashSet<>();
        if(lophoc != null){
            hockySet = lophoc.getHockySet();
            sinhVienSet = lophoc.getSinhvienSet();
        }
        Lophoc newLophoc = modelMapper.map(request, Lophoc.class);
        newLophoc.setHockySet(hockySet);
        newLophoc.setSinhvienSet(sinhVienSet);
        newLophoc.setIdHeDaoTao(heDaoTaoRepository.findById(request.getIdHeDaoTao()).orElse(null));
        newLophoc.setIdKhoaHoc(khoaRepository.findById(request.getIdKhoaHoc()).orElse(null));
        newLophoc.setIdNganh(nghanhDaoTaoRepository.findById(request.getIdNganh()).orElse(null));
        return newLophoc;
    }
}