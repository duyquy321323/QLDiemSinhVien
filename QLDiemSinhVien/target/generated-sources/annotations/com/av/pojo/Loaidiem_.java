package com.av.pojo;

import com.av.pojo.Diem;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-03-19T16:05:38", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Loaidiem.class)
public class Loaidiem_ { 

    public static volatile SingularAttribute<Loaidiem, String> tenDiem;
    public static volatile SingularAttribute<Loaidiem, Integer> idLoaiDiem;
    public static volatile SetAttribute<Loaidiem, Diem> diemSet;

}