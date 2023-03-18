package com.wz.jiebaos.mapper;

import com.wz.jiebaos.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.wz.jiebaos.pojo.User
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}




