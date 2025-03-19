package com.av.repository;

import com.av.pojo.MonhocHocky;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MonhocHocKyRepository {
    public MonhocHocky findById(Integer idMonHocHocKy);
}
