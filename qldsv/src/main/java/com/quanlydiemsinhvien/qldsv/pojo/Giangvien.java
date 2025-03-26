/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author FPTSHOP
 */
@Entity
@Table(name = "giangvien")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Giangvien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGiangVien;

    @Column(nullable = false, length = 50)
    private String hoTen;

    private Date ngaySinh;

    @Column(nullable = false)
    private short gioiTinh;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 100)
    private String diaChi;

    @Column(nullable = false, length = 11)
    private String soDienThoai;

    @OneToMany(mappedBy = "idGiangVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonhocHocky> monhocHockySet;

    private String idTaiKhoan;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGiangVien != null ? idGiangVien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giangvien)) {
            return false;
        }
        Giangvien other = (Giangvien) object;
        if ((this.idGiangVien == null && other.idGiangVien != null) || (this.idGiangVien != null && !this.idGiangVien.equals(other.idGiangVien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quanlydiemsinhvien.qldsv.pojo.Giangvien[ idGiangVien=" + idGiangVien + " ]";
    }
}
