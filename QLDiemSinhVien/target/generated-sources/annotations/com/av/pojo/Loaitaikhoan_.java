package com.av.pojo;

import com.av.pojo.Taikhoan;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Loaitaikhoan.class)
public class Loaitaikhoan_ { 

    public static volatile SingularAttribute<Loaitaikhoan, Integer> idloaitaikhoan;
    public static volatile SingularAttribute<Loaitaikhoan, String> tenloaitaikhoan;
    public static volatile SetAttribute<Loaitaikhoan, Taikhoan> taikhoanSet;

}