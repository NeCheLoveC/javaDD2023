package com.digdes.school.parser;

import com.digdes.school.d.TypeOperator;

public class Operator extends OperatorAbstract
{
    private TypeOperator typeOperator;

    public Operator(TypeOperator typeOperator)
    {
        this.typeOperator = typeOperator;
    }

    @Override
    public boolean isColumnName() {
        return false;
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
        return true;
    }
}
