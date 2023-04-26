package com.digdes.school.parser;

import com.digdes.school.d.TypeBooleanOperator;

public class BoolOperator extends OperatorAbstract implements PartOfCondition
{
    static private BoolOperator operatorAnd = new BoolOperator(TypeBooleanOperator.AND);
    static private BoolOperator operatorOr = new BoolOperator(TypeBooleanOperator.OR);
    //OR | AND
    private TypeBooleanOperator typeOperator;

    private BoolOperator(TypeBooleanOperator typeOperator)
    {
        this.typeOperator = typeOperator;
    }

    public static BoolOperator getOperatorAnd()
    {
        return operatorAnd;
    }

    public static BoolOperator getOperatorOr()
    {
        return operatorOr;
    }

    public TypeBooleanOperator getTypeOperator()
    {
        return this.typeOperator;
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
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }
}
