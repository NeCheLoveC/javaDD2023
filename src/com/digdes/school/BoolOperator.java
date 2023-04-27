package com.digdes.school;

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
    public boolean execute(ConstOperand firstOperand, ConstOperand secondOperand) {
        if(!firstOperand.getTypeValue().equals(Boolean.class) || !secondOperand.getTypeValue().equals(Boolean.class))
            throw new RuntimeException("Ошибка допустимых значений:" + firstOperand.getValue() + typeOperator.toString() + secondOperand.getValue());
        boolean firstOperandBoolean = Boolean.getBoolean(firstOperand.getValue());
        boolean secondOperandBoolean = Boolean.getBoolean(secondOperand.getValue());
        switch (this.typeOperator)
        {
            case AND:
                return firstOperandBoolean && secondOperandBoolean;
            case OR:
                return firstOperandBoolean || secondOperandBoolean;
            default:
                throw new RuntimeException("Оператор не распознан");
        }
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
