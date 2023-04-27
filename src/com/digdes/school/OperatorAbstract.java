package com.digdes.school;

public abstract class OperatorAbstract implements Comparable<OperatorAbstract>, PartOfCondition
{
    @Override
    public int compareTo(OperatorAbstract o) {
        if(this instanceof Operator && o instanceof Operator)
            return 0;
        else if(this instanceof Operator && o instanceof BoolOperator)
            return 1;
        else if(this instanceof BoolOperator && o instanceof Operator)
            return -1;
        else if(this instanceof BoolOperator && o instanceof BoolOperator)
        {
            BoolOperator boolOperatorThis = (BoolOperator) this;
            BoolOperator boolOperatorThat = (BoolOperator) o;
            if(boolOperatorThis.getTypeOperator().equals(boolOperatorThat.getTypeOperator()))
                return 0;
            else
            {
                if(boolOperatorThis.getTypeOperator().equals(TypeBooleanOperator.AND))
                    return 1;
                else
                {
                    return -1;
                }
            }
        }
        else
            throw new RuntimeException("Ошибка сравнения");
    }

    public abstract boolean execute(ConstOperand firstOperand, ConstOperand secondOperand);

}
