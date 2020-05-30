package com.zzhwork.springboot.service;

import com.zzhwork.springboot.data.Goods;
import com.zzhwork.springboot.data.User;
import com.zzhwork.springboot.entity.usertable;

public interface UserService {
     //登录用
     public int login(int account,int password,int admin);
     //注册用
     //public int addUser(usertable usertable);
     public int findSameCount(int account);
     //上架商品用
     public int findSameId(int id,String publisher);
     public int addNum(int id,String name,String publisher);
     int exitOrder(int account);
     //
}
