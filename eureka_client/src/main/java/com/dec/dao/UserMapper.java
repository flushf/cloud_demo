package com.dec.dao;


import com.dec.entity.User;

import java.util.List;


public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User user);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //这个方式我自己加的
    List<User> selectAllUser();
}