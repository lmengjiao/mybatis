package com.lin.dao;

import com.lin.entity.Person;
import com.lin.entity.PersonDTO;

import java.util.List;
import java.util.Map;

public interface PersonDao {
    //单表查询
    //全查
     List<Person> selectAll();
     //单查 性别查询 参数就是where后边的值
    List<Person> selectBySex(int sex);
    //查总条数 主查返回类型
    long selectCount();
    //查询女生总条数并且分数>100（单表 所以参数是person 实体类传参）
    //当前端传来多个参数 如果是同一个类中的属性（单表） 直接可以用这个类做参数
    //如果不是同一个类（多表） 可以手写一个参数的实体类（DTO）或者使用map做参数 map可以接收任何数据
    long selectByss01(Person person);
    //查询女生且生日<2020-09-07的人 (模拟多表 参数是map)
    List<Person> selectBysb01(Map map);
    //查询 分值最高的人是谁 子查询
    List<Person> selectBys();
    //所有女生男生的平均分值是多少 分组查询 实体类
    List<PersonDTO> selectByavg();
    //所有女生男生的平均分值是多少并且分数>200 有参数分数查询
    List<PersonDTO> selectByavg02(int score);
    //所有女生男生的平均分值是多少并且分数>200 有参数分数查询 map
    List<Map> selectByavg03(int score);
    //查询姓孙的 模糊查询 $不建议
    List<Person> selectByName(String name);
    //查询姓孙的 模糊查询 concat
    List<Person> selectByName02(String name);
    //查询姓孙的 模糊查询
    List<Person> selectByName03(String name);

    //增加
    int insertPerson(Person person);
}
