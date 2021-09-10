import com.lin.entity.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
mybatis执行流程：1.导包 2.连接数据库 3.sqlSessionFactory调用sqlSession 4.sqlession可以调用对象
 */
public class MybatisTest {
    private SqlSession sqlSession;
    @Before //在进行test注解之前执行的方法 提取重复代码的
    public void before() throws IOException {
        //加载并读取xml
        String path="SqlMapConfig.xml";
        InputStream is = Resources.getResourceAsStream(path);
        //sql连接的工厂类
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = sqlSessionFactory.openSession();
        System.out.println("sqlSession = " + sqlSession);
    }

    //全查 无参数
    @Test
    public void test01(){
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectAll");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //单查 性别查询 带参数
    @Test
    public void test02(){
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectBySex", 2);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
         sqlSession.close();
        }
        
    //查总条数 主要学习返回值类型 无参数
    @Test
    public void test03(){
        //selectOne返回值是一条数据
        long o = sqlSession.selectOne("com.lin.dao.PersonDao.selectCount");
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //查询女生总条数并且分数>100
    @Test
    public void test04(){
        Person person = new Person();
        person.setScore(100);
        person.setGender(2);
        Object o = sqlSession.selectOne("com.lin.dao.PersonDao.selectByss01", person);
        System.out.println("o = " + o);
        sqlSession.close();
    }

    ////查询女生且生日>2020-09-07的人 (模拟多表 参数是map) map传参
    @Test
    public void test05() throws ParseException {
        String date="2020-10-14";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date birthday=sdf.parse(date);
        Map map=new HashMap();
        map.put("gender",2); //key要和值一致\
        map.put("birthday",birthday);
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectBysb01", map);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //查询 分值最高的人是谁 子查询
    @Test
    public void test06(){
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectBys");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //所有女生男生的平均分值是多少 分组查询
    @Test
    public void test07(){
        List<PersonDTO> personDTOS = sqlSession.selectList("com.lin.dao.PersonDao.selectByavg");
        for (PersonDTO personDTO : personDTOS) {
            System.out.println("personDTO = " + personDTO);
        }
        sqlSession.close();
    }

    //所有女生男生的平均分值是多少并且分数>200 有参数分数查询
    @Test
    public void test08(){
        List<PersonDTO> personDTOS = sqlSession.selectList("com.lin.dao.PersonDao.selectByavg02", 200);
        for (PersonDTO personDTO : personDTOS) {
            System.out.println("personDTO = " + personDTO);
        }
        sqlSession.close();
    }

    //所有女生男生的平均分值是多少并且分数>200 有参数分数查询 map
    @Test
    public void test09(){
        List<Map> maps = sqlSession.selectList("com.lin.dao.PersonDao.selectByavg03", 200);
        for (Map map : maps) {
            System.out.println("map = " + map);
        }
        sqlSession.close();
    }

    //查询姓孙的 模糊查询
    @Test
    public void test10(){
        //There is no getter for property named 'name' in 'class java.lang.String' 因为$是拼接的 getter没有这个概念 #有
      //  List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectByName", "孙");
        Map map=new HashMap();
        map.put("name","孙");
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectByName", map);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    ////查询姓孙的 模糊查询 concat
    @Test
    public void test11(){
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectByName02", "孙");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }
    //模糊查询 常用
    @Test
    public void test12(){
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectByName03", "孙");
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //增加
    @Test
    public void test13(){
        Person person = new Person();
        person.setName("余庆伟");
        person.setGender(1);
        person.setBirthday(new Date());
        person.setAddress("湖北宜昌");
        person.setScore(123);
        int insert = sqlSession.insert("com.lin.dao.PersonDao.insertPerson", person);
        System.out.println("insert = " + insert);
        //增删改事务要提交
        sqlSession.commit();
        sqlSession.close();
    }

    //删除
    @Test
    public void test14(){
        int delete = sqlSession.delete("com.lin.dao.PersonDao.delPersonById", 13);
        System.out.println("delete = " + delete);
        sqlSession.commit();
        sqlSession.close();
    }

    //动态查询
    @Test
    public void test15(){
        Person person = new Person();
       // person.setId(17);
        person.setScore(200);
        person.setGender(2);
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.dongtaiselect", person);
      //全查  List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.dongtaiselect", null);
        for (Person person1 : personList) {
            System.out.println("person1 = " + person1);
        }
        sqlSession.close();
    }

    //动态修改
    @Test
    public void test16(){
        Person person = new Person();
        person.setId(1);
        person.setAddress("郑州");
        person.setBirthday(new Date());
        int update = sqlSession.update("com.lin.dao.PersonDao.dongtaiupdate", person);
        System.out.println("update = " + update);
        sqlSession.commit();
        sqlSession.close();
    }

    //批量删除
    @Test
    public void test17(){
        //构造ids
        List<Integer> idList=new ArrayList<>();
        idList.add(1);
        idList.add(14);
        Map map = new HashMap();
        map.put("ids",idList);
        int delete = sqlSession.delete("com.lin.dao.PersonDao.dongtaidelete", map);
        System.out.println("delete = " + delete);
        sqlSession.commit();
        sqlSession.close();
    }

    //count(*)
    @Test
    public void test18(){
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria=example.createCriteria(); //用例子类实现查询的规则
        //当example的criteria不用赋值的时候 SQL=select * from human;
        //SQL=select * from human where gender=2;
        //criteria.andGenderEqualTo(2);
        //SQL=select * from human where gender=2 and address='西京';
        //criteria.andAddressEqualTo("西京");
        //模糊查询 西京的人有几个
       // criteria.andAddressLike("%"+"西京"+"%");
        //查询家住在北京或者or分数是555 不同类型
        //example.or().andAddressEqualTo("北京");
        //example.or().andScoreEqualTo(555.0);
        //查询id=1 or =4 or =5  同类型
        List<Integer> ids=new ArrayList<>();
        ids.add(1);
        ids.add(4);
        ids.add(5);
        criteria.andIdIn(ids);
        Object o = sqlSession.selectOne("com.lin.dao.HumanDAO.countByExample", example);
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //*
    @Test
    public void test19(){
        //全查
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria(); //规则
        //女性
        //criteria.andGenderEqualTo(2);
        //lect * from human where gender = 1;
       // criteria.andGenderEqualTo(1);
        //lect * from human where id = 1;
       // criteria.andIdEqualTo(1);
        //lect * from human where score > 100;
        //criteria.andScoreGreaterThan(100.0);
        //lect * from human where score > 100 and gender = 1;
//        criteria.andScoreGreaterThan(100.0);
//        criteria.andGenderEqualTo(1);
        //lect * from human where score > 100 and gender = 1 and address like "%西京%";
        criteria.andScoreGreaterThan(100.0);
        criteria.andGenderEqualTo(1);
        criteria.andAddressLike("%"+"西京"+"%");
        List<Human> humans = sqlSession.selectList("com.lin.dao.HumanDAO.selectByExample", example);
        for (Human human : humans) {
            System.out.println("human = " + human);
        }
        sqlSession.close();
    }

    //动态增加
    @Test
    public void test20(){
        Human human = new Human();
        human.setName("林甜甜");
        human.setGender(2);
        human.setAddress("开封");
        int insert = sqlSession.insert("com.lin.dao.HumanDAO.insertSelective", human);
        System.out.println("insert = " + insert);
        sqlSession.commit();
        sqlSession.close();
    }

    //按主键删除
    @Test
    public void test21(){
        int delete = sqlSession.delete("com.lin.dao.HumanDAO.deleteByPrimaryKey", 1);
        System.out.println("delete = " + delete);
        sqlSession.commit();
        sqlSession.close();
    }

    //按条件删除
    //删除分数小于20的女生
    @Test
    public void test22(){
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andGenderEqualTo(2);
        criteria.andScoreLessThan(20.0);
        int delete = sqlSession.delete("com.lin.dao.HumanDAO.deleteByExample", example);
        System.out.println("delete = " + delete);
        sqlSession.commit();
        sqlSession.commit();
    }

    //按主键id修改一个对象
    @Test
    public void test23(){
        Human human = new Human();
        human.setId(4);
        human.setName("孙大圣");
        int update = sqlSession.update("com.lin.dao.HumanDAO.updateByPrimaryKeySelective", human);
        System.out.println("update = " + update);
        sqlSession.commit();
        sqlSession.close();
    }

    //批量的动态修改
    //不能测试
    @Test
    public void test24(){
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        sqlSession.update("com.lin.dao.HumanDAO.updateByExampleSelective",example);
    }
    
    //按主键id查
    @Test
    public void test25(){
        Human o = sqlSession.selectOne("com.lin.dao.HumanDAO.selectByPrimaryKey", 4);
        System.out.println("o = " + o);
        sqlSession.close();
    }

    //动态查询
    //查询分数>100并且生日>2020-11-04并且是女生
    @Test
    public void test26() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2020-11-04");
        HumanExample example = new HumanExample();
        HumanExample.Criteria criteria = example.createCriteria();
        criteria.andScoreGreaterThan(100.0);
        criteria.andBirthdayGreaterThan(date);
        criteria.andGenderEqualTo(2);
        List<Human> humans = sqlSession.selectList("com.lin.dao.HumanDAO.selectByExample", example);
        for (Human human : humans) {
            System.out.println("human = " + human);
        }
        sqlSession.close();
    }
    
    //一对多
    @Test
    public void test27(){
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectOrderByPersonId", 2);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    //一对多  方法1： 写在1方  改成动态sql 按id,name 都可以查
    @Test
    public void test28(){
        Map map=new HashMap();
        //map.put("id",2);
        map.put("name","策策");
        List<Person> personList = sqlSession.selectList("com.lin.dao.PersonDao.selectByin", map);
        for (Person person : personList) {
            System.out.println("person = " + person);
        }
        sqlSession.close();
    }

    // 作业2： 写出两张表  city--- 郑州， 区表 中原区，管城区， 开发区， 写出 1 对多的动态sql
    @Test
    public void test29(){
        Map map=new HashMap();
        //map.put("cid",2);
        map.put("cname","武汉");
        List<City> cities = sqlSession.selectList("com.lin.dao.CityDAO.selectByin", map);
        for (City city : cities) {
            System.out.println("city = " + city);
        }
        sqlSession.close();
    }

}
