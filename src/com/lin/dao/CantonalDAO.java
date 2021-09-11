package com.lin.dao;

import com.lin.entity.Cantonal;

public interface CantonalDAO {
    int deleteByPrimaryKey(Integer qid);

    int insert(Cantonal record);

    int insertSelective(Cantonal record);

    Cantonal selectByPrimaryKey(Integer qid);

    int updateByPrimaryKeySelective(Cantonal record);

    int updateByPrimaryKey(Cantonal record);


}