package com.zzhwork.springboot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Integer id;
    private String goodsname;
    private Integer goodsprice;
    private Integer goodsnum;
    private String goodspublisher;
    private String type;
}
