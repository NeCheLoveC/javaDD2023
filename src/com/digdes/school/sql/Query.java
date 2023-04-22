package com.digdes.school.sql;

import com.digdes.school.Table;

import java.util.List;
import java.util.Map;

public interface Query
{
    public List<Map<String,Object>> execute(Table table);
}
