package com.digdes.school;

import com.digdes.school.sql.FactoryQuery;
import com.digdes.school.sql.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter implements Table
{
    private FactoryQuery factoryQuery;
    //Object - {Long, Double, Boolean, String}
    private List<Map<String, Object>> items;
    private Map<String, Class<? extends Object>> structure;
    public JavaSchoolStarter()
    {
        factoryQuery = new FactoryQuery();
        items = new ArrayList<Map<String, Object>>();
        structure = new HashMap<>();
        structure.put("id",Long.class);
        structure.put("name",String.class);
    }

    public List<Map<String, Object>> execute(String rawQuery)
    {
        if(rawQuery == null || rawQuery.isEmpty() || rawQuery.isBlank())
            throw new RuntimeException("Запрос пуст...");
        rawQuery = rawQuery.trim();
        Query formedQuery = factoryQuery.createQuery(rawQuery);
        return formedQuery.execute(this);
    }



}
