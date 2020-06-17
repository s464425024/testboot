package org.chaoyue.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {

    public Map getMap();
}
