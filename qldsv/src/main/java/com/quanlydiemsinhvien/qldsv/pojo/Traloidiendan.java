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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "traloidiendan")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Traloidiendan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTraLoiDienDan;

    @Column(length = 400)
    private String noiDungTraLoi;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Cauhoidiendang idCauHoiDienDan;

    private String idTaiKhoan;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTraLoiDienDan != null ? idTraLoiDienDan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Traloidiendan)) {
            return false;
        }
        Traloidiendan other = (Traloidiendan) object;
        if ((this.idTraLoiDienDan == null && other.idTraLoiDienDan != null) || (this.idTraLoiDienDan != null && !this.idTraLoiDienDan.equals(other.idTraLoiDienDan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.quanlydiemsinhvien.qldsv.pojo.Traloidiendan[ idTraLoiDienDan=" + idTraLoiDienDan + " ]";
    }
}
