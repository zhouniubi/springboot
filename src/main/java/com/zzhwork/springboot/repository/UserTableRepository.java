package com.zzhwork.springboot.repository;

import com.zzhwork.springboot.entity.usertable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface UserTableRepository extends JpaRepository<usertable,Integer> {
    //查询个人信息(用于登录匹配)
    public usertable findByAccountAndPasswordAndAdmin(int account, int password,int admin);
   // public usertable save(int account, String name, int password);
    public usertable findByAccount(int account);
    public usertable findByAccountAndPassword(int account,int pwd);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update usertable user set user.admin = ?1 where user.account = ?2")
    void updateUserType(int admin,int account);

    //usertable deleteByAccount(int account);


}
