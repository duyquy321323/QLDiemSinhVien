/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.pojo;

import java.io.Serializable;
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
@Table(name = "cauhoidiendang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cauhoidiendang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCauHoiDienDan;

    @Column(nullable = false, length = 300)
    private String noiDungCauHoi;

    @Column(nullable = false, length = 100)
    private String ngayTao;

    @OneToMany(mappedBy = "idCauHoiDienDan", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Traloidiendan> traloidiendanSet;

    private String idTaiKhoan;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCauHoiDienDan != null ? idCauHoiDienDan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cauhoidiendang)) {
            return false;
        }
        Cauhoidiendang other = (Cauhoidiendang) object;
        if ((this.idCauHoiDienDan == null && other.idCauHoiDienDan != null) || (this.idCauHoiDienDan != null && !this.idCauHoiDienDan.equals(other.idCauHoiDienDan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quanlydiemsinhvien.qldsv.pojo.Cauhoidiendang[ idCauHoiDienDan=" + idCauHoiDienDan + " ]";
    }
    
}
