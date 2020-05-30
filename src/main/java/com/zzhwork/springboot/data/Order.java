package com.zzhwork.springboot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderid;
    private int id;
    private String ordername;
    private int orderprice;
    private int ordernum;
    private String orderarea;
    private int account;
}
