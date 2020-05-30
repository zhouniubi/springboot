package com.zzhwork.springboot.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class goodstable {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String goodsname;
    @Column
    private int goodsprice;
    @Column
    private int goodsnum;
    @Column
    private String goodspublisher;
    @Column
    private String type;
}
