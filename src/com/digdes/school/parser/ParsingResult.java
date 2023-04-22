package com.digdes.school.parser;

import com.digdes.school.utils.SqlOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingResult
{
    SqlOperator operator;

    private Map<String,Operand> valuesIntoOperator;
    String condition;

    public ParsingResult()
    {
        this.valuesIntoOperator = new HashMap<String, Operand>();
    }

    public ParsingResult(SqlOperator operator, Map<String, Operand> values, String condition) {
        this();
        this.operator = operator;
        this.valuesIntoOperator = values;
        this.condition = condition;
    }

    public boolean valuesMapHasKey(String key)
    {
        return this.valuesIntoOperator.containsKey(key);
    }

    public void addValuesIntoMap(String key, Operand operand)
    {
        this.valuesIntoOperator.put(key,operand);
    }
}
