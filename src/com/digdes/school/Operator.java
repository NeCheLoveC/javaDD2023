package com.digdes.school;

import java.util.regex.Pattern;

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

    @Override
    public boolean execute(ConstOperand firstOperand, ConstOperand secondOperand) {
        Object firstOperandObj = firstOperand.getObjValue();
        Object secondOperandObj = secondOperand.getObjValue();
        switch (typeOperator)
        {
            //'test' like '%t'
            case LIKE:
                if(firstOperandObj == null || secondOperandObj == null)
                    throw new RuntimeException("Ошибка (WHERE):" + firstOperand.getValue() + "LIKE" + secondOperand.getValue());
                if(!firstOperand.getTypeValue().equals(String.class) || !secondOperand.getTypeValue().equals(String.class))
                    throw new RuntimeException("Неверные типы для оператора LIKE");
                return firstOperand.getValue().matches(convertToRegEx(secondOperand.getValue()));
            case ILIKE:
                if(firstOperandObj == null || secondOperandObj == null)
                    throw new RuntimeException("Ошибка (WHERE):" + firstOperand.getValue() + "ILIKE" + secondOperand.getValue());
                if(!firstOperand.getTypeValue().equals(String.class) || !secondOperand.getTypeValue().equals(String.class))
                    throw new RuntimeException("Неверные типы для оператора ILIKE");
                Pattern pattern = Pattern.compile(convertToRegEx(secondOperand.getValue()), Pattern.CASE_INSENSITIVE);
                return pattern.matcher(firstOperand.getValue()).matches();
            case EQ:
                if(firstOperand.getObjValue() == secondOperand.getObjValue())
                    return true;
                if(firstOperandObj instanceof Number && secondOperandObj instanceof Number)
                {
                    return ((Number)firstOperandObj).doubleValue() == ((Number)secondOperandObj).doubleValue();
                }
                if(firstOperandObj.getClass() != secondOperandObj.getClass())
                    return false;
                else
                    return firstOperandObj.equals(secondOperandObj);
            case GT:
                //if(firstOperandObj == null || secondOperandObj == null)
                    //throw new RuntimeException("Ошибка (WHERE):" + firstOperand.getValue() + ">" + secondOperand.getValue());
                if(firstOperandObj instanceof Number && secondOperandObj instanceof Number)
                    return Double.valueOf(firstOperand.getValue()) > Double.valueOf(secondOperand.getValue());
                else
                    throw new RuntimeException("Ошибка (WHERE): " + firstOperand.getValue() + " > " + secondOperand.getValue());
            case GTE:
                //if(firstOperandObj == null || secondOperandObj == null)
                    //throw new RuntimeException("Ошибка (WHERE):" + firstOperand.getValue() + " >= " + secondOperand.getValue());
                if(firstOperandObj instanceof Number && secondOperandObj instanceof Number)
                    return Double.valueOf(firstOperand.getValue()) >= Double.valueOf(secondOperand.getValue());
                else
                    throw new RuntimeException("Ошибка (WHERE): " + firstOperand.getValue() + " >= " + secondOperand.getValue());
            case LT:
                //if(firstOperandObj == null || secondOperandObj == null)
                    //throw new RuntimeException("Ошибка (WHERE):" + firstOperand.getValue() + "<" + secondOperand.getValue());
                if(firstOperandObj instanceof Number && secondOperandObj instanceof Number)
                    return Double.valueOf(firstOperand.getValue()) < Double.valueOf(secondOperand.getValue());
                else
                    throw new RuntimeException("Ошибка (WHERE): " + firstOperand.getValue() + " < " + secondOperand.getValue());
            case LTE:
                //if(firstOperandObj == null || secondOperandObj == null)
                    //throw new RuntimeException("Ошибка (WHERE):" + firstOperand.getValue() + "<=" + secondOperand.getValue());
                if(firstOperandObj instanceof Number && secondOperandObj instanceof Number)
                    return Double.valueOf(firstOperand.getValue()) <= Double.valueOf(secondOperand.getValue());
                else
                    throw new RuntimeException("Ошибка (WHERE): " + firstOperand.getValue() + " <= " + secondOperand.getValue());
            case NE:
                if(firstOperandObj instanceof Number && secondOperandObj instanceof Number)
                {
                    return Double.valueOf(firstOperand.getValue()) != Double.valueOf(secondOperand.getValue());
                }
                else if((firstOperandObj != null && secondOperand == null) || (firstOperandObj == null && secondOperand != null))
                {
                    return true;
                }
                else
                {
                    return (firstOperandObj != secondOperandObj) || (!firstOperandObj.equals(secondOperandObj));
                }
            default:
                throw new RuntimeException("Оператор на распознан " + typeOperator.toString());
        }
    }

    private String convertToRegEx(String likePattern)
    {
        StringBuilder result = new StringBuilder();
        for(int i = 0;i < likePattern.length();i++)
        {
            char ch = likePattern.charAt(i);
            if(ch == '%')
                result.append(".*");
            else
                result.append(ch);
        }
        return result.toString();
    }
}
