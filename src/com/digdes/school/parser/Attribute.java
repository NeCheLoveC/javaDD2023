package com.digdes.school.parser;

import javax.lang.model.type.NullType;

public class Attribute<T>
{
    private String key;
    //private Value<T> value;
    private Object value;

    private Attribute(){}

    /*
    public Attribute(String key,Value<T> value)
    {

    }

     */


    public static Attribute createInstanceByKeyValueAndTypeValue(String key, String value, Class typeValue)
    {
        Attribute attr = new Attribute();
        attr.key = key;
        if(typeValue.equals(Long.class))
        {
            attr.value = (Long) Long.valueOf(value);
        }
        else if(typeValue.equals(Double.class))
        {
            attr.value = (Double) Double.valueOf(value);

        }
        else if(typeValue.equals(String.class))
        {
            attr.value = value;
        }
        else if(typeValue.equals(Boolean.class))
        {
            attr.value = (Boolean) Boolean.valueOf(value);
        }
        else if(typeValue.equals(NullType.class))
        {
            attr.value = null;
        }
        return attr;
    }


    public String getKey() {
        return key;
    }
    public Object getValue() {
        return value;
    }
}
