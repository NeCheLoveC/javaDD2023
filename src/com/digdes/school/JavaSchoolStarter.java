package com.digdes.school;

import javax.lang.model.type.NullType;
import java.util.*;

public class JavaSchoolStarter implements Table
{
    //Object - {Long, Double, Boolean, String}
    private List<Map<String, Object>> items;
    private Map<String, Class<? extends Object>> structure;
    public JavaSchoolStarter()
    {
        items = new ArrayList<Map<String, Object>>();
        structure = new HashMap<>();
        structure.put("id",Long.class);
        structure.put("lastName",String.class);
        structure.put("age",Long.class);
        structure.put("cost",Double.class);
        structure.put("active",Boolean.class);
    }

    public List<Map<String, Object>> execute(String rawQuery)
    {
        if(rawQuery == null || rawQuery.isEmpty() || rawQuery.isBlank())
            throw new RuntimeException("Запрос пуст...");
        rawQuery = rawQuery.trim();
        ParserEngine parserEngine = new ParserEngine(rawQuery);
        ParsingResult parsingResult = parserEngine.parse();
        validate(parsingResult,this);
        if(parsingResult.getOperator() == SqlOperator.SELECT)
        {
            return executeSelect(parsingResult);
        }
        else if(parsingResult.getOperator() == SqlOperator.INSERT)
        {
            return executeInsert(parsingResult);
        }
        else if(parsingResult.getOperator() == SqlOperator.UPDATE)
        {
            return executeUpdate(parsingResult);
        }
        else if(parsingResult.getOperator() == SqlOperator.DELETE)
        {
            return executeDelete(parsingResult);
        }
        else
            throw new RuntimeException("Оператор SQL запроса не найден");
    }

    private List<Map<String, Object>> executeUpdate(ParsingResult parsingResult)
    {
        List<Map<String, Object>> itemsForUpdate = executeSelect(parsingResult);
        Map<String, ConstOperand> values = parsingResult.getValuesIntoOperator();
        List<Map<String, Object>> result = new LinkedList<>();
        for(int i = 0;i< itemsForUpdate.size();i++)
        {
            Map<String, Object> item = itemsForUpdate.get(i);
            Map<String, Object> itemCopy = new HashMap<>(item);;
            for(Map.Entry<String, ConstOperand> value : values.entrySet())
            {
                if(value.getValue().getObjValue() == null)
                {
                    //Удалить строку если есть
                    if(itemCopy.containsKey(value.getKey()))
                        itemCopy.remove(value.getKey());
                }
                else
                {
                    if(structure.get(value.getKey()).equals(Double.class) && value.getValue().getTypeValue().equals(Long.class))
                    {
                        itemCopy.put(value.getKey(),Double.valueOf(value.getValue().getValue()));
                    }
                    else
                    {
                        itemCopy.put(value.getKey(), value.getValue().getObjValue());
                    }
                }
            }
            if(itemCopy.isEmpty())
                System.out.println("Объект должен иметь хотя бы одно не null поле!");
                //throw new RuntimeException("Объект должен иметь хотя бы одно не null поле!");
            else
            {
                itemsForUpdate.set(i,itemCopy);
                result.add(itemCopy);
            }
        }
        return result;
    }

    private List<Map<String, Object>> executeInsert(ParsingResult parsingResult)
    {
        List<Map<String, Object>> result = new LinkedList<>();
        //Iterator<Map<String, Object>> itr = items.iterator();
        Map<String, Object> newItem = new HashMap<>();
        Map<String, ConstOperand> values = parsingResult.getValuesIntoOperator();
        for(Map.Entry<String, ConstOperand> entry : values.entrySet())
        {
            String key = entry.getKey();
            ConstOperand operand = entry.getValue();
            if(operand.getObjValue() == null)
                continue;
            else if(getColumnTypeByName(key).equals(Double.class) && operand.getTypeValue().equals(Long.class))
                newItem.put(entry.getKey(),Double.valueOf(operand.getValue()));
            else
                newItem.put(entry.getKey(),entry.getValue().getObjValue());
        }
        if(newItem.isEmpty())
            throw new RuntimeException("Объект должен иметь хотя бы одно поле не равным null");
        this.items.add(newItem);
        result.add(newItem);
        return result;
    }


    private List<Map<String, Object>> executeSelect(ParsingResult parsingResult)
    {

        List<Map<String, Object>> result = new LinkedList<>();
        Iterator<Map<String, Object>> itr = items.iterator();
        while(itr.hasNext())
        {
            Map<String, Object> currentItem = itr.next();
            if(isRightCondition(parsingResult.getPostfixConditionForm(),currentItem))
                result.add(currentItem);
        }
        return result;
    }

    private List<Map<String, Object>> executeDelete(ParsingResult parsingResult)
    {
        List<Map<String, Object>> result = new LinkedList<>();
        Iterator<Map<String, Object>> itr = items.iterator();
        while(itr.hasNext())
        {
            Map<String, Object> currentItem = itr.next();
            if(isRightCondition(parsingResult.getPostfixConditionForm(),currentItem))
            {
                result.add(currentItem);
                itr.remove();
            }
        }
        return result;
    }


    private boolean isRightCondition(List<PartOfCondition> conditionList, Map<String, Object> item)
    {
        if(conditionList.size() == 0)
            return true;
        Stack<PartOfCondition> stackPartOfCondition = new Stack<>();
        Iterator<PartOfCondition> iteratorCondition = conditionList.iterator();
        while(iteratorCondition.hasNext())
        {
            PartOfCondition cond = iteratorCondition.next();
            if(cond.isConstOperand() || cond.isColumnName())
            {
                stackPartOfCondition.push(cond);
            }
            else if(cond.isOperator() || cond.isBooleanOperator())
            {
                PartOfCondition secondOperand = stackPartOfCondition.pop();
                PartOfCondition firstOperand = stackPartOfCondition.pop();
                ConstOperand firstConstOperand = null;
                ConstOperand secondConstOperand = null;
                if(firstOperand.isConstOperand())
                    firstConstOperand = (ConstOperand)firstOperand;
                else
                {
                    firstConstOperand = convertToConstOperand(firstOperand, item);
                }
                if(secondOperand.isConstOperand())
                    secondConstOperand = (ConstOperand)secondOperand;
                else
                {
                    secondConstOperand = convertToConstOperand(secondOperand, item);
                }
                stackPartOfCondition.push(new ConstOperand(Boolean.toString(((OperatorAbstract) cond).execute(firstConstOperand,secondConstOperand)),Boolean.class));
            }
        }
        if(stackPartOfCondition.size() != 1)
            throw new RuntimeException("Стэк не содержит последний (результирующий) элемент");
        return Boolean.valueOf(((ConstOperand)(stackPartOfCondition.pop())).getValue());
    }

    private ConstOperand convertToConstOperand(PartOfCondition operand,Map<String, Object> item)
    {
        if(!(operand instanceof ColumnName))
            throw new RuntimeException("Ошибка преобразования");
        String theColumnName = ((ColumnName)operand).getColumnName();
        return new ConstOperand(item.get(theColumnName).toString(), structure.get(theColumnName));
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

    @Override
    public boolean existColumn(String columnName) {
        return structure.containsKey(columnName);
    }

    public Class getColumnTypeByName(String columnName)
    {
        return structure.get(columnName);
    }

    @Override
    public Class getTypeColumnByColumnName(String columnName) {
        return structure.get(columnName);
    }
}
