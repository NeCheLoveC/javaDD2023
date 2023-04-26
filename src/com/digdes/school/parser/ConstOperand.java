package com.digdes.school.parser;

public class ConstOperand implements PartOfCondition
{
    private String value;
    private Class typeValue;

    public ConstOperand(String value, Class typeValue)
    {
        this.value = value;
        this.typeValue = typeValue;
    }

    public String getValue() {
        return value;
    }

    public Class getTypeValue() {
        return typeValue;
    }

    @Override
    public boolean isColumnName() {
        return false;
    }

    @Override
    public boolean isConstOperand() {
        return true;
    }

    @Override
    public boolean isOpenBracket() {
        return false;
    }

    @Override
    public boolean isClosingBracket() {
        return false;
    }

    @Override
    public boolean isBooleanOperator() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return false;
    }
}
