/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.quanlydiemsinhvien.qldsv.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "giaovu")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Giaovu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idGiaoVu;

    @Column(nullable = false, length = 50)
    private String tenGiaoVu;

    @Column(nullable = false)
    private short gioiTinh;

    @Column(nullable = false, length = 11)
    private String soDienThoai;

    private String idTaiKhoan;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGiaoVu != null ? idGiaoVu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giaovu)) {
            return false;
        }
        Giaovu other = (Giaovu) object;
        if ((this.idGiaoVu == null && other.idGiaoVu != null) || (this.idGiaoVu != null && !this.idGiaoVu.equals(other.idGiaoVu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quanlydiemsinhvien.qldsv.pojo.Giaovu[ idGiaoVu=" + idGiaoVu + " ]";
    }
}
