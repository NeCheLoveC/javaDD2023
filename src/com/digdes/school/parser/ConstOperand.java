package com.digdes.school.parser;

import javax.lang.model.type.NullType;

public class ConstOperand implements PartOfCondition
{
    private String value;
    private Object objValue;
    private Class typeValue;

    public ConstOperand(String value, Class typeValue)
    {
        this.value = value;
        this.typeValue = typeValue;
        if(typeValue.equals(Long.class))
        {
            this.objValue = Long.valueOf(value);
        }
        else if(typeValue.equals(String.class))
        {
            this.objValue = value;
        }
        else if(typeValue.equals(Boolean.class))
        {
            this.objValue = Boolean.valueOf(value);
        }
        else if(typeValue.equals(Double.class))
        {
            this.objValue = Double.valueOf(value);
        }
        else if(typeValue.equals(NullType.class))
        {
            this.objValue = null;
        }
        else
        {
            throw new RuntimeException("Класс '" + typeValue.getName() + "' не поддерживается данной системой");
        }
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

    public Object getObjValue() {
        return objValue;
    }
}
