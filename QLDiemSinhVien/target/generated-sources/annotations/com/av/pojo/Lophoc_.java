package com.av.pojo;

import com.av.pojo.Hedaotao;
import com.av.pojo.Hocky;
import com.av.pojo.Khoa;
import com.av.pojo.Nganhdaotao;
import com.av.pojo.Sinhvien;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Lophoc.class)
public class Lophoc_ { 

    public static volatile SingularAttribute<Lophoc, Hedaotao> idHeDaoTao;
    public static volatile SingularAttribute<Lophoc, Integer> idLopHoc;
    public static volatile SetAttribute<Lophoc, Hocky> hockySet;
    public static volatile SingularAttribute<Lophoc, Khoa> idKhoaHoc;
    public static volatile SingularAttribute<Lophoc, Nganhdaotao> idNganh;
    public static volatile SingularAttribute<Lophoc, String> tenLopHoc;
    public static volatile SetAttribute<Lophoc, Sinhvien> sinhvienSet;

}