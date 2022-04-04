package com.fenglin.login.dao

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.fenglin.login.domain.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserDao : BaseMapper<User>{

}