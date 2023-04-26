package com.digdes.school.parser;

import com.digdes.school.utils.SqlOperator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParsingResult
{
    SqlOperator operator;

    private Map<String, ConstOperand> valuesIntoOperator;
    private List<PartOfCondition> postfixConditionForm;

    public ParsingResult()
    {
        this.valuesIntoOperator = new HashMap<String, ConstOperand>();
        this.postfixConditionForm = new LinkedList<>();
    }

    public ParsingResult(SqlOperator operator, Map<String, ConstOperand> values, List<PartOfCondition> postfixConditionForm) {
        this.operator = operator;
        this.valuesIntoOperator = values;
        this.postfixConditionForm = postfixConditionForm;
    }

    public boolean valuesMapHasKey(String key)
    {
        return this.valuesIntoOperator.containsKey(key);
    }

    public void addValuesIntoMap(String key, ConstOperand operand)
    {
        this.valuesIntoOperator.put(key,operand);
    }

    public List<PartOfCondition> getPostfixConditionForm() {
        return postfixConditionForm;
    }

    public void setPostfixConditionForm(List<PartOfCondition> postfixConditionForm) {
        this.postfixConditionForm = postfixConditionForm;
    }
}
