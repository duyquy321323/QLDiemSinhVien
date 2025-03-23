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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "sinhvien")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sinhvien implements Serializable {

    @OneToMany(mappedBy = "idSinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Monhocdangky> monhocdangkySet;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSinhVien;

    @Column(length = 50)
    private String hoTen;

    private Date ngaySinh;

    @Column(length = 100)
    private String diaChi;

    private Short gioiTinh;

    @Column(length = 11)
    private String soDienThoai;

    @Column(length = 50)
    private String email;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Lophoc maLop;

    @JoinColumn
    @OneToOne(cascade=CascadeType.ALL)
    private Taikhoan idTaiKhoan;

    @Transient
    private Integer maXacNhan;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSinhVien != null ? idSinhVien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sinhvien)) {
            return false;
        }
        Sinhvien other = (Sinhvien) object;
        if ((this.idSinhVien == null && other.idSinhVien != null) || (this.idSinhVien != null && !this.idSinhVien.equals(other.idSinhVien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quanlydiemsinhvien.qldsv.pojo.Sinhvien[ idSinhVien=" + idSinhVien + " ]";
    }
}
