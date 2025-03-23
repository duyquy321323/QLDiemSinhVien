package com.quanlydiemsinhvien.qldsv.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlydiemsinhvien.qldsv.pojo.MonhocHocky;
import com.quanlydiemsinhvien.qldsv.pojo.Monhocdangky;
import com.quanlydiemsinhvien.qldsv.pojo.Sinhvien;

public interface MonhocdangkyRepository extends JpaRepository<Monhocdangky, Integer> {
    List<Monhocdangky> findByIdSinhVien(Sinhvien IdSinhVien);

    Optional<Monhocdangky> findByIdSinhVienAndIdMonHoc(Sinhvien IdSinhVien, MonhocHocky idMonHoc);

    List<Monhocdangky> findByIdMonHocInAndIdSinhVien(List<MonhocHocky> idMonHoc,Sinhvien IdSinhVien);

    List<Monhocdangky> findByIdMonHoc(MonhocHocky idMonHoc);

    List<Monhocdangky> findByIdSinhVien_IdSinhVienAndTrangThaiAndKhoaMon(String IdSinhVien, Short trangThai, Short khoaMon);

    List<Monhocdangky> findByIdMonHocInAndIdSinhVienAndIdMonHoc_IdHocky_NgayDangKyLessThanEqualAndIdMonHoc_IdHocky_NgayHetHanGreaterThanEqual(List<MonhocHocky> idMonHoc, Sinhvien IdSinhVien, Date currentDate, Date currentDate2);
}
