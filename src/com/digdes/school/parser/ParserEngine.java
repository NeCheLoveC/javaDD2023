package com.digdes.school.parser;

import com.digdes.school.utils.SqlOperator;

import javax.lang.model.type.NullType;

public class ParserEngine
{
    private Character ch = null;
    private String query;
    private int id = -1;
    boolean outWord = true;
    private ParsingResult result = new ParsingResult();

    public ParserEngine(String query)
    {
        this.query = query.trim();
    }

    private void next()
    {
        if(hasNext())
            this.ch = query.charAt(++id);
        else
        {
            this.ch = null;
        }
    }

    private boolean hasNext()
    {
        int sizeQuery = this.query.length();
        if((--sizeQuery) > id)
            return true;
        return false;
    }
    public ParsingResult parse()
    {
        next();
        space();
        defineTypeQuery();
        if(queryOperatorIsInsertOrUpdate())
        {
            expectedOneSpaceChar();
            space();
            expectedVALUES();
            expectedOneSpaceChar();
            space();
            fillTheResultWithValues();
            space();
        }
        space();
        if(ch != null)
            throw new RuntimeException("Ошибка валидации : СТРОКА не валидна (не достигут конец)");
        return this.result;
    }

    private boolean queryOperatorIsInsertOrUpdate()
    {
        return this.result.operator.equals(SqlOperator.INSERT) || this.result.operator.equals(SqlOperator.UPDATE);
    }

    // \s*
    private void space()
    {
        while(this.ch != null && Character.isWhitespace(ch))
        {
            next();
        }
    }

    private void defineTypeQuery()
    {
        //select
        if(ch == null)
            throw new RuntimeException();
        outWord = false;
        if(ch == 's' || ch == 'S')
        {
            currentCharCaseInsEqAndDoNextChar('s',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('l', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('c', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.SELECT;
        }
        //update
        else if(ch == 'u' || ch == 'U')
        {
            currentCharCaseInsEqAndDoNextChar('u',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('p',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('d', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('a', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.UPDATE;
        }
        //insert
        else if(ch == 'i' || ch == 'I')
        {
            currentCharCaseInsEqAndDoNextChar('i',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('n',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('s', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('r', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.INSERT;
        }
        //delete
        else if(ch == 'd' || ch == 'D')
        {
            currentCharCaseInsEqAndDoNextChar('d',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('l', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsEqAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.DELETE;
        }
        else
        {
            throw new RuntimeException("Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
        }
        outWord = true;
    }

    private void expectedOneSpaceChar()
    {
        if(ch == null || !Character.isWhitespace(ch))
            throw new RuntimeException("Ожидался пробельный символ");
        next();
    }
    private void expectedVALUES()
    {
        currentCharCaseInsEqAndDoNextChar('v',"Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsEqAndDoNextChar('a', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsEqAndDoNextChar('l', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsEqAndDoNextChar('u',"Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsEqAndDoNextChar('e', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsEqAndDoNextChar('s', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
    }
    private boolean currentCharCaseInsEqAndDoNextChar(Character character, String errorMessage)
    {
        if(this.ch == null)
            throw new RuntimeException(errorMessage);
        character = Character.toLowerCase(character);
        Character lowerCaseCurrentChar = Character.toLowerCase(ch);
        if(lowerCaseCurrentChar.equals(character))
        {
            next();
            return true;
        }
        else
        {
            throw new RuntimeException("Ошибка валидации");
        }
    }

    //'columnName'
    private String columnName()
    {
        StringBuilder columnName = new StringBuilder();
        if (ch != null && ch == '\'') {
            outWord = false;
            next();
        }
        else{
            throw new RuntimeException();
        }
        if (ch != null && (Character.isLetter(ch) || ch.equals('_'))) {
            columnName.append(ch);
            next();
        } else {
            throw new RuntimeException();
        }
        while (ch != null && (Character.isLetter(ch) || ch.equals('_') || Character.isDigit(ch)))
        {
            columnName.append(ch);
            next();
        }
        if(ch != null && ch == '\'')
        {
            outWord = true;
            next();
        }
        else
        {
            throw new RuntimeException();
        }
        return columnName.toString();
    }
    private void fillTheResultWithValues()
    {
        String firstColumnName = columnName();
        space();
        currentCharCaseInsEqAndDoNextChar('=', "Ошибка валидации (после VALUES)");
        space();
        Operand firstOperand = getOperand();
        if(!result.valuesMapHasKey(firstColumnName))
            result.addValuesIntoMap(firstColumnName,firstOperand);
        else
            throw new RuntimeException("Ошибка валидации. В строке VALUES обнаружены повторяющиеся названия колонок");
        while(ch != null && ch != 'w')
        {
            space();
            currentCharCaseInsEqAndDoNextChar(',',"");
            space();
            String columnName = columnName();
            space();
            currentCharCaseInsEqAndDoNextChar('=', "Ошибка валидации (после VALUES)");
            space();
            Operand operand = getOperand();
            if(!result.valuesMapHasKey(columnName))
                result.addValuesIntoMap(columnName,operand);
            else
                throw new RuntimeException("Ошибка валидации. В строке VALUES обнаружены повторяющиеся названия колонок");
        }
    }

    private Operand getOperand()
    {
        String valueOperand = null;
        Class operandType = null;
        if(ch == null)
            throw new RuntimeException("Ошибка валидации");
        if(ch == '\'')
        {
            valueOperand = sequenceOfCharsInQuotes();
            operandType = String.class;
        }
        //Целое число или Дробное число
        else if(Character.isDigit(ch))
        {
            outWord = false;
            valueOperand = expectedLongValue();
            //Встретилась '.' - тогда операнд имеет тип Double
            if(ch != null && ch == '.')
            {
                valueOperand = valueOperand + ".";
                next();
                valueOperand = valueOperand + expectedLongValue();
                operandType = Double.class;
            }
            //Операнд имеет тип Long
            else
            {
                operandType = Long.class;
            }
            outWord = true;

        }
        else if(ch == '.')
        {
            outWord = false;
            valueOperand = doubleStartingWithDot();
            operandType = Double.class;
            outWord = true;
        }
        //null
        else if(ch == 'n' || ch == 'N')
        {
            outWord = false;
            valueOperand = expectedNull();
            operandType = NullType.class;
            outWord = true;
        }
        //Boolean
        else if(ch == 'f' || ch == 'F' || ch == 't' || ch == 'T')
        {
            outWord = false;
            valueOperand = expectedBoolean();
            operandType = Boolean.class;
            outWord = true;
        }
        else
        {
            throw new RuntimeException("Ошибка валидации, неверное значение в строке оператора после VALUES (не распознан тип присваемого значения)");
        }
        return new Operand(valueOperand,operandType);
    }


    //'sequenceOfChars' -> sequenceOfChars
    private String sequenceOfCharsInQuotes()
    {
        StringBuilder valueString = new StringBuilder();
        outWord = false;
        if(ch == null || ch != '\'')
        {
            throw new RuntimeException("Ошибка валидации");
        }
        next();
        //Значение строки внутри кавычек
        while(ch != null && ch != '\'')
        {
            valueString.append(ch);
            next();
        }
        if(ch != '\'')
            throw new RuntimeException("Ошибка валидации в операторе после VALUES (нарушение парности кавычек)");
        else
            outWord = true;
        next();
        return valueString.toString();
    }

    //.4 == 0.4
    private String doubleStartingWithDot()
    {
        StringBuilder valueDouble = new StringBuilder();
        if(ch == null || ch != '.')
        {
            throw new RuntimeException("Ошибка валидации");
        }
        valueDouble.append("0.");
        next();
        if(ch != null && Character.isDigit(ch))
        {
            valueDouble.append(ch);
            next();
        }
        else {
            throw new RuntimeException("Ошибка в строке оператора после VALUES (некорректно записано дробное число)");
        }
        while(ch != null && Character.isDigit(ch))
        {
            valueDouble.append(ch);
            next();
        }
        return valueDouble.toString();
    }

    private String expectedLongValue()
    {
        if(ch == null)
            throw new RuntimeException("Ошибка валидации");
        StringBuilder valueLong = new StringBuilder();
        if(Character.isDigit(ch))
            valueLong.append(ch);
        next();
        while(ch != null && Character.isDigit(ch))
        {
            valueLong.append(ch);
            next();
        }
        return valueLong.toString();
    }

    private String expectedBoolean()
    {
        if(ch == null)
            throw new RuntimeException();
        next();
        String booleanValue = null;
        if(ch == 't' || ch == 'T')
        {
            currentCharCaseInsEqAndDoNextChar('r',"");
            currentCharCaseInsEqAndDoNextChar('u',"");
            currentCharCaseInsEqAndDoNextChar('e',"");
            booleanValue = "true";
        }
        else if(ch == 'f' || ch == 'F')
        {
            currentCharCaseInsEqAndDoNextChar('a',"");
            currentCharCaseInsEqAndDoNextChar('l',"");
            currentCharCaseInsEqAndDoNextChar('s',"");
            currentCharCaseInsEqAndDoNextChar('e',"");
            booleanValue = "false";
        }
        else
        {
            throw new RuntimeException();
        }

        return booleanValue;
    }

    private String expectedNull()
    {
        currentCharCaseInsEqAndDoNextChar('n',"");
        currentCharCaseInsEqAndDoNextChar('u',"");
        currentCharCaseInsEqAndDoNextChar('l',"");
        currentCharCaseInsEqAndDoNextChar('l',"");
        return "null";
    }
}
