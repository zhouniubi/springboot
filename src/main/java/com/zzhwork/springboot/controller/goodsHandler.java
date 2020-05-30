package com.zzhwork.springboot.controller;

import com.zzhwork.springboot.entity.goodstable;
import com.zzhwork.springboot.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class goodsHandler {
    @Autowired
    private GoodsRepository goodsRepository;
    @RequestMapping("/findAll")
    public List<goodstable> findAll(){
        return goodsRepository.findAll();
    }
}
