package com.lin.dao;

import com.lin.entity.Person;

import java.util.List;

public interface PersonDao {
    //全查
     List<Person> selectAll();
}
