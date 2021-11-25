package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;

@Mapper
public interface UserMapper {
    ///@Insert("insert into user {name, account_id, token, gmt_create, gmt_modified } values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified}")
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, avatar_url) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl});")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findByID(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountID(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmtModified}, avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
}
