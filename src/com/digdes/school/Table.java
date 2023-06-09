package com.digdes.school;

import java.util.List;
import java.util.Map;

public interface Table
{
    public List<Map<String, Object>> execute(String query);
    public boolean existColumn(String columnName);
    public Class getTypeColumnByColumnName(String columnName);
}
