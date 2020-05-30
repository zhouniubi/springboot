package com.zzhwork.springboot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ordertable {
    @Id
    private int orderid;
    @Column
    private  int id;
    @Column
    private String ordername;
    @Column
    private int orderprice;
    @Column
    private int ordernum;
    @Column
    private String orderarea;
    @Column
    private int account;
}
