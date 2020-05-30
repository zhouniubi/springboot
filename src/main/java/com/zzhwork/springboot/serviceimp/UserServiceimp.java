package com.zzhwork.springboot.serviceimp;

import com.zzhwork.springboot.data.Goods;
import com.zzhwork.springboot.data.User;
import com.zzhwork.springboot.entity.goodstable;
import com.zzhwork.springboot.entity.ordertable;
import com.zzhwork.springboot.entity.usertable;
import com.zzhwork.springboot.repository.GoodsRepository;
import com.zzhwork.springboot.repository.OrderRespository;
import com.zzhwork.springboot.repository.UserTableRepository;
import com.zzhwork.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimp implements UserService {
    @Autowired
    UserTableRepository user;
    @Autowired
    GoodsRepository goods;
    @Autowired
    OrderRespository order;

    public int login(int account, int password,int admin) {
        int res=1;
        usertable adminusertable = this.user.findByAccountAndPasswordAndAdmin(account,password,1);
        usertable commonusertable = this.user.findByAccountAndPasswordAndAdmin(account,password,0);

        if(commonusertable!=null) {
            res= 10;
            }
        else if(adminusertable!=null){
            res= 11;
             }
        else
            res= 0;
        return res;
    }




    @Override
    public int findSameCount(int account) {
        usertable usertable = this.user.findByAccount(account);
        if(usertable == null){
            return 1;
        }
        else{
            return 0;
        }
    }
//用于增加商品数量(可能被放弃)
    @Override
    public int findSameId(int id,String publisher) {
        int num;
        goodstable goodstable = this.goods.findByIdAndGoodspublisher(id,publisher);
        if(goodstable == null){
            num = 0;

        }
        else {
            num = this.goods.findByIdAndGoodspublisher(id,publisher).getGoodsnum();
        }
        return num;
    }

    @Override
    public int addNum(int id, String name, String publisher) {
        int num = 0;
        goodstable goodstable = this.goods.findByIdAndGoodsnameAndGoodspublisher(id,name,publisher);
        if(goodstable!=null){
            num = goodstable.getGoodsnum();
        }
        return num;
    }

    @Override
    public int exitOrder(int account) {
        List<ordertable> list = this.order.findByOrderAccount(account);
        if(list.size()==0){
            return 0;
        }
        else {
            System.out.println(list);
            return 1;

        }
    }


}
