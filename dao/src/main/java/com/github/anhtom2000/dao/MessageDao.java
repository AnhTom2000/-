package com.github.anhtom2000.dao;

import com.github.anhtom2000.bean.Message;
import com.github.anhtom2000.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao {

    @Insert("INSERT INTO tb_message(name,to,content,isPull) VALUES(#{message.name},#{message.to},#{content},#{isPull})")
    public void addMessage(@Param("message") Message message);

    @Select("SELECT to,content,isPull FROM tb_message WHERE to = #{user.name} AND isPull = 0 ")
    public List<Message> getMessage(@Param("user") User user);

    @Update("UPDATE tb_message SET isPull = 1 WHERE to = #{name}")
    public void updateMessage(@Param("name") String name);
}
