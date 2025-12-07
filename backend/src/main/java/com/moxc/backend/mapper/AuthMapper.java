package com.moxc.backend.mapper;

import com.moxc.backend.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthMapper {

    @Select("select id, username, password, personality_type as personalityType from user where username = #{username}")
    User findByUsername(@Param("username") String username); // 接收来自service层的username，然后赋值给这里sql语句中的username
}
