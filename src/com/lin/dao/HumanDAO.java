package com.lin.dao;

import com.lin.entity.Human;
import com.lin.entity.HumanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
// dao层就是增删改查
public interface HumanDAO {
    //当example类为null的时候 SQL语句=select * from human
    //当example不为null的时候（查询女生） SQL语句=select * from human where gender=2
    long countByExample(HumanExample example); //使用example例子类动态sql查询总条数

    int deleteByExample(HumanExample example); //

    int deleteByPrimaryKey(Integer id);

    int insert(Human record);

    int insertSelective(Human record);

    List<Human> selectByExample(HumanExample example);

    Human selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Human record, @Param("example") HumanExample example);

    int updateByExample(@Param("record") Human record, @Param("example") HumanExample example);

    int updateByPrimaryKeySelective(Human record);

    int updateByPrimaryKey(Human record);
}