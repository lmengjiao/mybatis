package com.lin.dao;

import com.lin.entity.City;

import java.util.List;
import java.util.Map;

public interface CityDAO {
    int deleteByPrimaryKey(Integer cid);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    // 作业2： 写出两张表  city--- 郑州， 区表 中原区，管城区， 开发区， 写出 1 对多的动态sql
    List<City> selectByin(Map map);
}