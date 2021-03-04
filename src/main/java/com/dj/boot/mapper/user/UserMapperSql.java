package com.dj.boot.mapper.user;

import com.dj.boot.pojo.User;

public class UserMapperSql {

    public String findUserById(Integer id){
        String sql = "select * from dj_user where id = " +id;
        return sql;
    }
}
