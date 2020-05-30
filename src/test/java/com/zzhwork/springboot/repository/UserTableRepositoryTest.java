package com.zzhwork.springboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTableRepositoryTest {
    @Autowired
    private UserTableRepository userTableRepository;

    @Test
    void addAll(){
        System.out.println("数据库信息是"+userTableRepository.findAll());
    }
    @Autowired
    private GoodsRepository goodsRepository;
    @Test
    void addAll2(){System.out.println("数据库读取的信息是"+goodsRepository.findAll());}
    @Test
    void testUser(){
        System.out.println();
    }
}