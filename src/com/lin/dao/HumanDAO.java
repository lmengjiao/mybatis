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

    int deleteByExample(HumanExample example); //按条件删除

    int deleteByPrimaryKey(Integer id); //按主键id删除

    int insert(Human record); //普通增加

    int insertSelective(Human record); //动态增加 用这个！！！

    List<Human> selectByExample(HumanExample example);

    Human selectByPrimaryKey(Integer id); //按主键id查一个对象

    int updateByExampleSelective(@Param("record") Human record, @Param("example") HumanExample example); //动态批量修改 用！！！

    int updateByExample(@Param("record") Human record, @Param("example") HumanExample example); //动态批量修改

    int updateByPrimaryKeySelective(Human record); //按主键id修改一个对象 用！！！

    int updateByPrimaryKey(Human record); //修改 会造成原有字段变成null 不能用 删库跑路
}