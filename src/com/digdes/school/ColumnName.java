package com.digdes.school;

public class ColumnName implements PartOfCondition
{
    private String columnName;

    public ColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }


    @Override
    public boolean isColumnName() {
        return true;
    }

    @Override
    public boolean isConstOperand() {
        return false;
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
