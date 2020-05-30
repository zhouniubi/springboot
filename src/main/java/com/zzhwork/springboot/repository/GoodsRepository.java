package com.zzhwork.springboot.repository;

import com.zzhwork.springboot.entity.goodstable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GoodsRepository extends JpaRepository<goodstable,Integer> {
    //上架商品用于查重
    public goodstable findByIdAndGoodspublisher(int id,String publisher);
    goodstable findByIdAndGoodsnameAndGoodspublisher(int id,String name,String publisher);
    public goodstable findById(int id);
    @Query(value = "select * from goodstable goods where goods.goodsnum>0 and goods.type='pass'",nativeQuery=true)
     List<goodstable> findPass();
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update goodstable goods set goods.goodsnum = ?1 where goods.id = ?2")
    void updateGoodsNum(int num,int id);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update goodstable goods set goods.type = ?1 where goods.id = ?2")
    void updateGoodsType(String type,int id);
    goodstable deleteById(int id);


}
