package com.av.pojo;

import com.av.pojo.MonhocHocky;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Monhoc.class)
public class Monhoc_ { 

    public static volatile SingularAttribute<Monhoc, Integer> idMonHoc;
    public static volatile SingularAttribute<Monhoc, String> tenMonHoc;
    public static volatile SetAttribute<Monhoc, MonhocHocky> monhocHockySet;
    public static volatile SingularAttribute<Monhoc, Integer> soTinChi;

}