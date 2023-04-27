package com.digdes.school;

public interface PartOfCondition
{
    public boolean isColumnName();
    public boolean isConstOperand();
    public boolean isOpenBracket();
    public boolean isClosingBracket();
    public boolean isBooleanOperator();
    public boolean isOperator();
}
