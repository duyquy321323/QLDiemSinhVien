package com.av.pojo;

import com.av.pojo.Taikhoan;
import com.av.pojo.Traloidiendan;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Cauhoidiendang.class)
public class Cauhoidiendang_ { 

    public static volatile SingularAttribute<Cauhoidiendang, String> noiDungCauHoi;
    public static volatile SetAttribute<Cauhoidiendang, Traloidiendan> traloidiendanSet;
    public static volatile SingularAttribute<Cauhoidiendang, Taikhoan> idTaiKhoan;
    public static volatile SingularAttribute<Cauhoidiendang, Integer> idCauHoiDienDan;
    public static volatile SingularAttribute<Cauhoidiendang, String> ngayTao;

}