package com.why.home.back_end.service;

import com.why.home.back_end.dao.UserRepository;
import com.why.home.back_end.entity.User;
import com.why.home.back_end.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.why.home.back_end.util.MD5Utils.code;

/*---------------------------------------------------------------
              UserServiceImpl Release 1.0
;  Copyright (c) 2020-2020 by why Co.
;  Written by why on 2020/8/22.
;
;  Function:
;                  UserService接口实现
----------------------------------------------------------------*/

/*------@Service表示该类是业务层-----*/
@Service
public class UserServiceImpl implements UserService{

    /*----注解 可以对类成员变量、方法及构造函数进行标注 让Spring完成bean自动装配工作---*/
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*------覆写定义好的接口方法-----*/
    /*------!!!!在业务逻辑层利用code静态方法对密码进行加密 以适配数据库中已经加密的密码!!!-----*/

    @Override
    public User checkUser(String username, String password) {
        /*------此处实现数据库逻辑操作（DAO层），拿到用户名密码查数据库，查到返回User，查不到返回空----*/
        User user=userRepository.findByUsernameAndPassword(username,code(password));
        /*------!!!此处返回DAO层处理后的user!!!-----*/
        return user;
    }
}
