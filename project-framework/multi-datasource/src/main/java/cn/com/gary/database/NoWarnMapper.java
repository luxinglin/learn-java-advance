package cn.com.gary.database;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: 解决[WARN][org.mybatis.spring.mapper.ClassPathMapperScanner][warn][44]-> No MyBatis mapper was found in [cn.com.gary.database]报错
 * @create 2020-01-09 10:49
 **/
@Mapper
public interface NoWarnMapper {
}
