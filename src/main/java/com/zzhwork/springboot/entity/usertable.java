package com.zzhwork.springboot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
//@Table(name = "usertable")
public class usertable {
    @Id             //设置主键
    private int account;
    @Column
    private String username;
    @Column
    private int  password;
    @Column
    private int  admin;


}
