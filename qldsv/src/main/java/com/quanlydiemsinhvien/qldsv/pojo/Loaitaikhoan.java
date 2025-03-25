// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */
// package com.quanlydiemsinhvien.qldsv.pojo;

// import java.io.Serializable;
// import java.util.Set;

// import javax.persistence.CascadeType;
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.OneToMany;
// import javax.persistence.Table;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// /**
//  *
//  * @author FPTSHOP
//  */
// @Entity
// @Table(name = "loaitaikhoan")
// @Builder
// @Setter
// @Getter
// @AllArgsConstructor
// @NoArgsConstructor
// public class Loaitaikhoan implements Serializable {

//     private static final long serialVersionUID = 1L;

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer idloaitaikhoan;

//     @Column(nullable = false, length = 1000)
//     private String tenloaitaikhoan;

//     @OneToMany(mappedBy = "chucVu", cascade = CascadeType.ALL, orphanRemoval = true)
//     private Set<Taikhoan> taikhoanSet;

//     @Override
//     public int hashCode() {
//         int hash = 0;
//         hash += (idloaitaikhoan != null ? idloaitaikhoan.hashCode() : 0);
//         return hash;
//     }

//     @Override
//     public boolean equals(Object object) {
//         // TODO: Warning - this method won't work in the case the id fields are not set
//         if (!(object instanceof Loaitaikhoan)) {
//             return false;
//         }
//         Loaitaikhoan other = (Loaitaikhoan) object;
//         if ((this.idloaitaikhoan == null && other.idloaitaikhoan != null) || (this.idloaitaikhoan != null && !this.idloaitaikhoan.equals(other.idloaitaikhoan))) {
//             return false;
//         }
//         return true;
//     }

//     @Override
//     public String toString() {
//         return "com.quanlydiemsinhvien.qldsv.pojo.Loaitaikhoan[ idloaitaikhoan=" + idloaitaikhoan + " ]";
//     }
// }
