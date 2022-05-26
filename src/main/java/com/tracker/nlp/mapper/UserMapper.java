package com.tracker.nlp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tracker.nlp.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description UserMapper
 * @Author Mister-Lu
 * @Date 2022/1/15
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
