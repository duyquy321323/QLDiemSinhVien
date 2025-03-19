/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.av.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author FPTSHOP
 */
@Entity
@Table(name = "taikhoan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Taikhoan.findAll", query = "SELECT t FROM Taikhoan t"),
    @NamedQuery(name = "Taikhoan.findByIdTaiKhoan", query = "SELECT t FROM Taikhoan t WHERE t.idTaiKhoan = :idTaiKhoan"),
    @NamedQuery(name = "Taikhoan.findByTenTaiKhoan", query = "SELECT t FROM Taikhoan t WHERE t.tenTaiKhoan = :tenTaiKhoan"),
    @NamedQuery(name = "Taikhoan.findByMatKhau", query = "SELECT t FROM Taikhoan t WHERE t.matKhau = :matKhau"),
    @NamedQuery(name = "Taikhoan.findByImage", query = "SELECT t FROM Taikhoan t WHERE t.image = :image")})
@Getter
@Setter
public class Taikhoan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTaiKhoan")
    private String idTaiKhoan;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TenTaiKhoan")
    private String tenTaiKhoan;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MatKhau")
    private String matKhau;
    @Size(max = 1000)
    @Column(name = "image")
    private String image;
    @OneToMany(mappedBy = "idTaiKhoan")
    @JsonIgnore
    private Set<Traloidiendan> traloidiendanSet;
    @JoinColumn(name = "ChucVu", referencedColumnName = "idloaitaikhoan")
    @ManyToOne
    private Loaitaikhoan chucVu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTaiKhoan")
    @JsonIgnore
    private Set<Cauhoidiendang> cauhoidiendangSet;
    @OneToOne(mappedBy = "idTaiKhoan")
    @JsonIgnore
    private Giangvien giangvien;
    @OneToOne(mappedBy = "idTaiKhoan")
    @JsonIgnore
    private Giaovu giaovu;
    @OneToOne(mappedBy = "idTaiKhoan")
    @JsonIgnore
    private Sinhvien sinhvien;
    @Transient
    private String xacNhanMk;
    @Transient
    private String mkMoi;
    @Transient
    private Giangvien giangvien1;

    @Transient
    private int maXacNhan;
    
    @Transient
    @JsonIgnore
    private MultipartFile file;
    public Taikhoan() {
    }

    public Taikhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public Taikhoan(String idTaiKhoan, String tenTaiKhoan, String matKhau) {
        this.idTaiKhoan = idTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTaiKhoan != null ? idTaiKhoan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taikhoan)) {
            return false;
        }
        Taikhoan other = (Taikhoan) object;
        if ((this.idTaiKhoan == null && other.idTaiKhoan != null) || (this.idTaiKhoan != null && !this.idTaiKhoan.equals(other.idTaiKhoan))) {
            return false;
        }
        return true;
    }
    
}
