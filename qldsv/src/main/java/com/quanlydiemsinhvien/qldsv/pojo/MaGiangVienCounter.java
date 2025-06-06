package com.quanlydiemsinhvien.qldsv.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaGiangVienCounter {
    @Id
    private Integer id;

    private Integer lastMaGv;
}