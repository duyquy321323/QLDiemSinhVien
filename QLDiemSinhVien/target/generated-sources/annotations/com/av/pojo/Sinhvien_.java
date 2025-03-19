package com.av.pojo;

import com.av.pojo.Lophoc;
import com.av.pojo.Monhocdangky;
import com.av.pojo.Taikhoan;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Sinhvien.class)
public class Sinhvien_ { 

    public static volatile SingularAttribute<Sinhvien, String> diaChi;
    public static volatile SingularAttribute<Sinhvien, String> soDienThoai;
    public static volatile SingularAttribute<Sinhvien, Lophoc> maLop;
    public static volatile SetAttribute<Sinhvien, Monhocdangky> monhocdangkySet;
    public static volatile SingularAttribute<Sinhvien, String> idSinhVien;
    public static volatile SingularAttribute<Sinhvien, Taikhoan> idTaiKhoan;
    public static volatile SingularAttribute<Sinhvien, Date> ngaySinh;
    public static volatile SingularAttribute<Sinhvien, String> hoTen;
    public static volatile SingularAttribute<Sinhvien, Short> gioiTinh;
    public static volatile SingularAttribute<Sinhvien, String> email;

}