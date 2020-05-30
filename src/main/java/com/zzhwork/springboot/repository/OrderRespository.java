package com.zzhwork.springboot.repository;


import com.zzhwork.springboot.entity.ordertable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRespository extends JpaRepository<ordertable,Integer> {
   // List<ordertable> findByOrderAccount(int account);
    @Query(value = "select * from ordertable o where o.account =:acc",nativeQuery=true)
    List<ordertable> findByOrderAccount(int acc);
    ordertable findByOrderid(int orderid);
    List<ordertable> findAllByOrderid(int orderid);

}
