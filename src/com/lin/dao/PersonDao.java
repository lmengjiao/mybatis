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

    //删除
    // 之后讲解动态SQL dao层只有基础类型 int String 不方便执行动态sql
    //以后自己写代码 参数一定是一个实体类或者map或者DTO
    int delPersonById(int id);

    //动态sql(重点) 一条xml中的语句可以实现N多查询。
    //要实现多种查询有一个硬性条件--参数要多
    //1.放弃单个属性（int string）改用实体类 2.参数改用map

    //第一类 特征 返回值-正常的结果集person类  都是select * from person开头
    //1.1 select * from person  if where没参数 就是全查
    //1.2 select from person where gender = 2  if where后面是gender 那就是单查gender
    //1.3 select * from person where gender = #{gender} and birthday < #{birthday}
    //1.4 select * from person where name like '${name}%'
    //1-4 可以合成一条 只需要把后面的参数做一个if判断
    List<Person> dongtaiselect(Person person); //动态sql如果参数不是实体类或集合 是个空参 那么没有任何意义
    //返回值是list<实体类> 参数也是同样的实体类 这是一个典型的动态sql语句

    //第二类 特征 返回值-一个数 单行单例 非person实体类 是一个数据类型  都是select count(*)from person开头
    //2.1 select count(*) from person
    //2.2 select count(*) from person where gender = 2 and score > 100

    //动态修改
    int dongtaiupdate(Person person);

    //批量删除
    void dongtaidelete(Map map);

}
