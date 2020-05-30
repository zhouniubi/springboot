package com.zzhwork.springboot.controller;

import com.zzhwork.springboot.data.Goods;
import com.zzhwork.springboot.data.Order;
import com.zzhwork.springboot.data.User;
import com.zzhwork.springboot.entity.goodstable;
import com.zzhwork.springboot.entity.ordertable;
import com.zzhwork.springboot.entity.usertable;
import com.zzhwork.springboot.repository.GoodsRepository;
import com.zzhwork.springboot.repository.OrderRespository;
import com.zzhwork.springboot.repository.UserTableRepository;
import com.zzhwork.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.zzhwork.springboot.data.Result;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@Slf4j

public class WebController {
    @Autowired
   // @Resource
    private UserService userService;
    @Autowired
    private UserTableRepository userTableRepository;
    @Autowired
    private  GoodsRepository goodsRepository;
    @Autowired
    private OrderRespository orderRespository;
    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public Result mul(@RequestBody User user){
        Result result = new Result();
        int account = user.getAccount();
        int password = user.getPassword();
        if(this.userTableRepository.findByAccountAndPassword(account,password)!=null){
            int admin = userTableRepository.findByAccountAndPassword(account,password).getAdmin();
            System.out.println("登录数据是账号："+account+"密码是："+password+"权限是"+admin);
        if(this.userService.login(account,password,admin)==11){
            result.setRes(11);
            System.out.println("管理员登录成功！");
        }
        else if(this.userService.login(account,password,admin)==10){
            result.setRes(10);
            System.out.println("普通用户登录成功！");
        }
        }
        else{
            result.setRes(00);
            System.out.println("没有注册！");
        }
        return result;
    }
    @CrossOrigin
    @PostMapping(value = "/reg")
    @ResponseBody
    public Result reg(@RequestBody User user){
        Result result = new Result();
        usertable usertable = new usertable();
        int account = user.getAccount();
        String name = user.getUsername();
        int password = user.getPassword();

        System.out.println("存入的账号是："+account+"用户名是："+name+"用户密码是"+password);
        if(this.userService.findSameCount(account)==1){
            usertable.setAccount(account);
            usertable.setUsername(name);
            usertable.setPassword(password);
            usertable.setAdmin(0);
            userTableRepository.save(usertable);
            System.out.println("注册成功！");
            result.setRes(1);
        }
        else {
            System.out.println("注册失败！");
            result.setRes(0);
        }
        return result;
    }

    @CrossOrigin
    @PostMapping(value = "/sell")
    @ResponseBody
    public Result sell(@RequestBody Goods goods){
        Result result = new Result();
        goodstable goodstable = new goodstable();
        int id = goods.getId();
        String name = goods.getGoodsname();
        int price = goods.getGoodsprice();
        int num = goods.getGoodsnum();
        String publisher = goods.getGoodspublisher();
        long n = goodsRepository.count();
            if ( id>n) {
                n= n+id;
                goodstable.setId(id);
                goodstable.setGoodsname(name);
                goodstable.setGoodsprice(price);
                goodstable.setGoodsnum(num);
                goodstable.setGoodspublisher(publisher);
                goodstable.setType("nopass");
                goodsRepository.save(goodstable);
                System.out.println("已添加未存在的商品！等待通过审核");
                result.setRes(11);
            }
             else if(userService.addNum(id,name,publisher)!=0&&id<=n) {

                        goodstable.setId(id);
                        goodstable.setGoodsname(name);
                        goodstable.setGoodsprice(price);
                        goodstable.setGoodsnum(num + this.userService.addNum(id, name,publisher));
                        goodstable.setGoodspublisher(publisher);
                        goodstable.setType("pass");
                        goodsRepository.save(goodstable);
                        System.out.println("已经存在的商品，更新数量！");
                        result.setRes(111);
                    }
             else if(userService.findSameId(id,publisher)==0)
                    {
                        System.out.println("与现有的产品产生了冲突！无法更新！");
                        result.setRes(000);
                    }
             else{
                    System.out.println("用户已经更新了原本的货物！");
                    result.setRes(110);
            }


        return result;
    }
    @CrossOrigin
    @RequestMapping(value = "/show")
    @ResponseBody
    public List<goodstable> show(){
            System.out.println("查询成功");
            return goodsRepository.findPass();
    }
    //用于前端的计数器
    @CrossOrigin
    @RequestMapping(value = "/count")
    @ResponseBody
    public long count(){
        return goodsRepository.count();
    }
    //用于展示某个账户的订单
    @CrossOrigin
    @RequestMapping(value = "/AccountOrder")
    @ResponseBody
    public List<ordertable> showOneOrder(@RequestBody Order order){
        int account = order.getAccount();
        if(userService.exitOrder(account)!=0){
            System.out.println("订单查询成功");
            System.out.println("账号是"+account);
            return orderRespository.findByOrderAccount(account);
        }
        else {
            System.out.println("未找到订单！");
            return null;
        }
    }
    //用于购买操作（减少商品表，增加订单）
    @CrossOrigin
    @RequestMapping(value = "/buy")
    @ResponseBody
    public Result buy(@RequestBody Order order){
        Result result = new Result();
        ordertable ordertable = new ordertable();
        //当前id对应的产品总数
        int goodsNum = goodsRepository.findById(order.getId()).getGoodsnum();
        int orderNum = order.getOrdernum();
        int id = order.getId();

        String ordername = order.getOrdername();
        int orderprice = order.getOrderprice();
        String orderarea = order.getOrderarea();
        int account = order.getAccount();

        //判断产品的数量是否满足订单的需求
        if(orderNum>goodsNum&&goodsNum>0){
            System.out.println("订单购买数量超过库存！不允许购买！");
            result.setRes(10);
        }
        else if(orderNum<=goodsNum&&goodsNum>0){
            goodsNum = goodsNum-orderNum;
            goodsRepository.updateGoodsNum(goodsNum,id);
            int orderid = (int) orderRespository.count();
            System.out.println("数据库已经更新！");
            ordertable.setId(id);
            ordertable.setOrderid(orderid+1);
            //System.out.println("orderid是"+orderid);
            ordertable.setOrdername(ordername);
            ordertable.setOrderprice(orderprice);
            ordertable.setOrdernum(orderNum);
            ordertable.setOrderarea(orderarea);
            ordertable.setAccount(account);
            orderRespository.save(ordertable);
            System.out.println("订单生成成功！");
            result.setRes(11);
        }
        else{
            System.out.println("商品不足！");
            result.setRes(00);
        }
        return result;
    }
    //用于显示所有的商品
    @CrossOrigin
    @RequestMapping(value = "/showAll")
    @ResponseBody
    public List<goodstable> all(){
        //显示所有的商品
        System.out.println("查找成功！");
        return goodsRepository.findAll();
    }
    //用于更新商品状态
    @CrossOrigin
    @RequestMapping(value = "/updateType")
    @ResponseBody
    public Result updateType(@RequestBody Goods goods){
        Result result = new Result();
        String type = goods.getType();
        int id = goods.getId();
        if(type.equals("pass")||type.equals("nopass")) {
            goodsRepository.updateGoodsType(type, id);
            System.out.println("更改权限成功！");
            result.setRes(1);
        }
        else{
            System.out.println("权限类型不存在，无法更改！");
            result.setRes(0);
        }
        return result;
    }
    //用于删除商品
    @CrossOrigin
    @RequestMapping(value = "/deleteGoods")
    @ResponseBody
    public Result delete(@RequestBody Goods goods){
        Result result = new Result();
        int id = goods.getId();
        goodsRepository.deleteById(id);
        System.out.println("删除成功！");
        result.setRes(1);
        return result;
    }
    @CrossOrigin
    @RequestMapping(value = "/showUser")
    @ResponseBody
    public List<usertable> showUser(){
        return userTableRepository.findAll();
    }
    //用于删除用户
    @CrossOrigin
    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestBody User user){
        Result result = new Result();
        int account = user.getAccount();
        usertable usertable = userTableRepository.findByAccount(account);
        userTableRepository.delete(usertable);
        System.out.println("删除成功！");
        result.setRes(1);
        return result;
    }
    //用于修改用户权限
    @CrossOrigin
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Result updateUser(@RequestBody User user){
        Result result = new Result();
        int admin = user.getAdmin();
        int account = user.getAccount();
        if(admin==0||admin==1) {
            userTableRepository.updateUserType(admin,account);
            System.out.println("更改权限成功！");
            result.setRes(1);
        }
        else{
            System.out.println("权限类型不存在，无法更改！");
            result.setRes(0);
        }
        return result;
    }
    //用于加载全部订单信息
    @CrossOrigin
    @RequestMapping(value = "/showOrder")
    @ResponseBody
    public List<ordertable> showOrder(){
        return orderRespository.findAll();
    }
    //用于删除订单
    @CrossOrigin
    @RequestMapping(value = "/deleteOrder")
    @ResponseBody
    public Result deleteOrder(@RequestBody Order order ){
        Result result = new Result();
        int orderid = order.getOrderid();
        ordertable ordertable = orderRespository.findByOrderid(orderid);
        orderRespository.delete(ordertable);
        System.out.println("删除成功！");
        result.setRes(1);
        return result;
    }

}
