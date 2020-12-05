package com.github.anhtom2000.dao;

import com.github.anhtom2000.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    @Insert("INSERT INTO tb_user(name,password) VALUES(#{user.name},#{user.password})")
    public void register(@Param("user") User user);

    @Select("SELECT name,password FROM tb_user WHERE name = #{name}")
    public User findUserByName(@Param("name") String name);
}
