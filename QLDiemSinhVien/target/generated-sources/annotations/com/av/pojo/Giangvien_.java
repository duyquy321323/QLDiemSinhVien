package com.av.pojo;

import com.av.pojo.MonhocHocky;
import com.av.pojo.Taikhoan;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Giangvien.class)
public class Giangvien_ { 

    public static volatile SingularAttribute<Giangvien, String> idGiangVien;
    public static volatile SingularAttribute<Giangvien, String> diaChi;
    public static volatile SingularAttribute<Giangvien, String> soDienThoai;
    public static volatile SingularAttribute<Giangvien, Taikhoan> idTaiKhoan;
    public static volatile SetAttribute<Giangvien, MonhocHocky> monhocHockySet;
    public static volatile SingularAttribute<Giangvien, Date> ngaySinh;
    public static volatile SingularAttribute<Giangvien, String> hoTen;
    public static volatile SingularAttribute<Giangvien, Short> gioiTinh;
    public static volatile SingularAttribute<Giangvien, String> email;

}