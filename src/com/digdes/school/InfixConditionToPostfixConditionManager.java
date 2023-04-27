package com.digdes.school;

import java.util.List;

public class InfixConditionToPostfixConditionManager
{

    public String condition;

    public InfixConditionToPostfixConditionManager(String condition) {
        this.condition = condition;
    }

    public List<PartOfCondition> execute() throws Exception {
        validateArg();
        throw new Exception();
    }

    private void validateArg()
    {
        if(condition == null)
            throw new NullPointerException();
        condition = condition.trim();
        if(condition.isBlank() || condition.isEmpty())
            throw new RuntimeException("Ожидалось выражение после WHERE");
    }


}
