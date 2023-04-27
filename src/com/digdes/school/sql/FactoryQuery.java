package com.digdes.school.sql;

import com.digdes.school.Table;
import com.digdes.school.parser.ColumnName;
import com.digdes.school.parser.ConstOperand;
import com.digdes.school.parser.ParsingResult;
import com.digdes.school.parser.PartOfCondition;

import javax.lang.model.type.NullType;
import java.util.Map;

public class FactoryQuery
{
    public Query createQuery(ParsingResult parsingResult, Table table)
    {
        validate(parsingResult, table);

        return null;
    }

    private void validate(ParsingResult parsingResult, Table table)
    {
        validateValues(parsingResult,table);
        validateWhere(parsingResult,table);
    }

    private void validateWhere(ParsingResult parsingResult, Table table)
    {
        for(PartOfCondition partOfCondition : parsingResult.getPostfixConditionForm())
        {
            if(partOfCondition.isColumnName())
            {
                ColumnName columnName = (ColumnName) partOfCondition;
                if(!table.existColumn(columnName.getColumnName()))
                    throw new RuntimeException("Таблица не содержит столбец (WHERE): '" + columnName.getColumnName() + "'");
            }
        }
    }

    private void validateValues(ParsingResult parsingResult, Table table)
    {
        Map<String, ConstOperand> values = parsingResult.getValuesIntoOperator();
        for(Map.Entry<String,ConstOperand> item : values.entrySet())
        {
            String columnName = item.getKey();
            ConstOperand operand = item.getValue();
            if(!table.existColumn(columnName))
                throw new RuntimeException("Таблица не содержит столбец : '" + columnName + "'");
            else
            {
                if(operand.getTypeValue().equals(NullType.class))
                    continue;
                Class columnType = table.getTypeColumnByColumnName(columnName);
                if(columnType == operand.getTypeValue() || (columnType == Double.class && (operand.getTypeValue() == Long.class)))
                {

                }
                else
                    throw new RuntimeException("Ошибка типа сприсваивания (VALUES), column name : " + columnName + ". Ожидался тип " + columnType.getName());
            }
        }
    }
}
