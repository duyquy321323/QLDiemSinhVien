package com.av.pojo;

import com.av.pojo.Khoadaotao;
import com.av.pojo.Lophoc;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Nganhdaotao.class)
public class Nganhdaotao_ { 

    public static volatile SingularAttribute<Nganhdaotao, String> tenNganhDaoTao;
    public static volatile SingularAttribute<Nganhdaotao, Khoadaotao> idKhoaDaoTao;
    public static volatile SetAttribute<Nganhdaotao, Lophoc> lophocSet;
    public static volatile SingularAttribute<Nganhdaotao, Integer> idNganhDaoTao;

}