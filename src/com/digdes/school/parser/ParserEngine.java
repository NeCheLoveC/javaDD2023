package com.digdes.school.parser;

import com.digdes.school.enum_utils.TypeOperator;
import com.digdes.school.enum_utils.SqlOperator;

import javax.lang.model.type.NullType;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ParserEngine
{
    private Character ch = null;
    private Character previousChar = null;
    private String query;
    private int id = -1;
    boolean outWord = true;
    private ParsingResult result = new ParsingResult();

    private Stack<PartOfCondition> operatorsStack = new Stack<>();
    private List<PartOfCondition> outResult = new LinkedList<>();

    public ParserEngine(String query)
    {
        this.query = query.trim();
    }

    private void next()
    {
        previousChar = ch;
        if(hasNext())
            this.ch = query.charAt(++id);
        else
        {
            this.ch = null;
        }
    }
    private boolean previousCharIsWhiteSpace()
    {
        if(!Character.isWhitespace(previousChar))
            throw new RuntimeException("");
        return true;
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
        if(queryOperatorIsInsertOrUpdate()) {
            space();
            previousCharIsWhiteSpace();
            expectedVALUES();
            space();
            previousCharIsWhiteSpace();
            fillTheResultWithValues();
            space();
        }
        space();
        if(queryOperatorIsSelectOrUpdateOrDelete() && hasNext())
        {
            previousCharIsWhiteSpace();
            expectedWhere();
            space();
            previousCharIsWhiteSpace();
            fillWhere();
            space();
        }
        space();
        if(ch != null)
            throw new RuntimeException("Ошибка валидации. Не достигнуть конец запроса при анализе.");
        return this.result;
    }

    private boolean queryOperatorIsSelectOrUpdateOrDelete()
    {
        return this.result.operator.equals(SqlOperator.SELECT)
                || this.result.operator.equals(SqlOperator.UPDATE)
                || this.result.operator.equals(SqlOperator.DELETE);
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
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('s',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('l', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('c', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.SELECT;
        }
        //update
        else if(ch == 'u' || ch == 'U')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('u',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('p',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('d', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('a', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.UPDATE;
        }
        //insert
        else if(ch == 'i' || ch == 'I')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('i',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('n',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('s', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('r', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.INSERT;
        }
        //delete
        else if(ch == 'd' || ch == 'D')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('d',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('l', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('t', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e', "Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
            this.result.operator = SqlOperator.DELETE;
        }
        else
        {
            throw new RuntimeException("Ошибка валидации: Не удалось распознать наименование оператора (SELECT | INSERT | UPDATE | DELETE");
        }
        outWord = true;
    }

    private boolean expectedOneSpaceChar()
    {
        if(ch == null || !Character.isWhitespace(ch))
            throw new RuntimeException("Ожидался пробельный символ");
        next();
        return true;
    }
    private void expectedVALUES()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('v',"Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('a', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('l', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('u',"Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('e', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('s', "Ошибка валидации: ожидалось 'values' (SELECT | INSERT | UPDATE | DELETE");
    }
    private boolean currentCharCaseInsensitiveEqCharacterAndDoNextChar(Character character, String errorMessage)
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
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('=', "Ошибка валидации (после VALUES)");
        space();
        ConstOperand firstOperand = getOperand();
        space();
        if(!result.valuesMapHasKey(firstColumnName))
            result.addValuesIntoMap(firstColumnName,firstOperand);
        else
            throw new RuntimeException("Ошибка валидации. В строке VALUES обнаружены повторяющиеся названия колонок");
        while(ch != null && ch != 'w')
        {
            space();
            currentCharCaseInsensitiveEqCharacterAndDoNextChar(',',this.id + "");
            space();
            String columnName = columnName();
            space();
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('=', "Ошибка валидации (после VALUES)");
            space();
            ConstOperand operand = getOperand();
            if(!result.valuesMapHasKey(columnName))
                result.addValuesIntoMap(columnName,operand);
            else
                throw new RuntimeException("Ошибка валидации. В строке VALUES обнаружены повторяющиеся названия колонок");
            space();
        }
    }

    private ConstOperand getOperand()
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
            throw new RuntimeException("Ошибка валидации,");
        }
        return new ConstOperand(valueOperand,operandType);
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
        String booleanValue = null;
        if(ch == 't' || ch == 'T')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('t',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('r',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('u',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"");
            booleanValue = "true";
        }
        else if(ch == 'f' || ch == 'F')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('f',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('a',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('l',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('s',"");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"");
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
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('n',"");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('u',"");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('l',"");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('l',"");
        return "null";
    }
    private boolean sqlOperatorIsInsert()
    {
        return result.operator.equals(SqlOperator.INSERT);
    }
    private void expectedWhere()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('w',"Ожидалось 'WHERE'");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('h',"Ожидалось 'WHERE'");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"Ожидалось 'WHERE'");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('r',"Ожидалось 'WHERE'");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"Ожидалось 'WHERE'");
    }




    private void addElementIntoStack(PartOfCondition partOfCondition)
    {
        if(partOfCondition.isConstOperand() || partOfCondition.isColumnName())
        {
           outResult.add(partOfCondition);
        }
        else if(partOfCondition.isOperator() || partOfCondition.isBooleanOperator())
        {
            if(operatorsStack.isEmpty())
                operatorsStack.push(partOfCondition);
            else
            {
                while(!operatorsStack.isEmpty())
                {
                    PartOfCondition a = operatorsStack.pop();
                    if(a.isOperator() || a.isBooleanOperator())
                    {
                        OperatorAbstract opTop = (OperatorAbstract) a;
                        OperatorAbstract opThis = (OperatorAbstract) partOfCondition;
                        int resultOfCompare = opTop.compareTo(opThis);
                        if(resultOfCompare < 0)
                        {
                            operatorsStack.push(opTop);
                        }
                        else
                        {
                            outResult.add(opTop);
                        }
                        if(resultOfCompare < 0)
                            break;
                    }
                }
                operatorsStack.push(partOfCondition);
            }
        }
    }
    private void fillWhere()
    {
        ColumnName firstColumnName = new ColumnName(columnName());
        space();
        Operator firstOperator = new Operator(expectedOperator());
        space();
        ConstOperand firstConstOperand = getOperand();
        space();
        addElementIntoStack(firstColumnName);
        addElementIntoStack(firstOperator);
        addElementIntoStack(firstConstOperand);
        while(hasNext())
        {
            space();
            BoolOperator booleanOperator = expectedBoolOperator();
            space();
            ColumnName columnName = new ColumnName(columnName());
            space();
            Operator operator = new Operator(expectedOperator());
            space();
            ConstOperand constOperand = getOperand();
            addElementIntoStack(booleanOperator);
            addElementIntoStack(columnName);
            addElementIntoStack(operator);
            addElementIntoStack(constOperand);
        }
        space();
        if(ch != null)
        {
            throw new RuntimeException("Ошибка валидации...Ожидался конец запроса после условия WHERE");
        }
        while(!operatorsStack.isEmpty())
        {
            PartOfCondition partOfCondition = operatorsStack.pop();
            outResult.add(partOfCondition);
        }
        this.result.setPostfixConditionForm(outResult);
    }

    private BoolOperator expectedBoolOperator()
    {
        if(ch == null)
            throw new RuntimeException("Ошибка валидации. Ожидался оператор (строкак WHERE)");
        if(ch == 'a' || ch == 'A')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('a',"Ошибка валидации...(строка WHERE)");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('n',"Ошибка валидации...(строка WHERE)");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('d',"Ошибка валидации...(строка WHERE)");
            return BoolOperator.getOperatorAnd();
        }
        else if(ch == 'o' || ch == 'O')
        {
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('o',"Ошибка валидации...(строка WHERE)");
            currentCharCaseInsensitiveEqCharacterAndDoNextChar('r',"Ошибка валидации...(строка WHERE)");
            return BoolOperator.getOperatorOr();
        }
        else
        {
            throw new RuntimeException("Ошибка валидации. Оператор не распознан (AND | OR");
        }
    }

    private TypeOperator expectedOperator()
    {
        if(ch == null)
            throw new RuntimeException("Ошибка валидации. Ожидался оператор между операндами (строка WHERE)");
        if(ch == 'l' || ch == 'L')
        {
            expectedLike();
            return TypeOperator.LIKE;
        }
        else if(ch == 'i' || ch == 'I')
        {
            expectedILike();
            return TypeOperator.ILIKE;
        }
        else if(ch == '=')
        {
            expectedEQ();
            return TypeOperator.EQ;
        }
        else if(ch == '!')
        {
            expectedNE();
            return TypeOperator.NE;
        }
        else if(ch == '>')
        {
            next();
            if(ch != null && ch == '=')
            {
                next();
                return TypeOperator.GTE;
            }
            else if (ch != null)
            {
                next();
                return TypeOperator.GT;
            }
            else
            {
                throw new RuntimeException("Ошибка валидации (строка WHERE)");
            }
        }
        else if(ch == '<')
        {
            next();
            if(ch != null && ch == '=')
            {
                next();
                return TypeOperator.LTE;

            }
            else if(ch != null)
            {
                next();
                return TypeOperator.LT;
            }
            else
                throw new RuntimeException("Ошибка валидации (строка WHERE)");
        }
        else
        {
            throw new RuntimeException("Ошибка валидации. Оператор на распознан");
        }
    }

    private void expectedLike()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('l',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('i',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('k',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"Ошибка валидации в строке WHERE");
    }

    private void expectedILike()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('i',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('l',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('i',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('k',"Ошибка валидации в строке WHERE");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('e',"Ошибка валидации в строке WHERE");
    }

    private void expectedEQ()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('=', "Ошибка валидации");
    }

    private void expectedNE()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('!', "Ошибка валидации");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('=', "Ошибка валидации");
    }

    private void expectedGT()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('>', "Ошибка валидации");
    }

    private void expectedLT()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('<', "Ошибка валидации");
    }

    private void expectedGTE()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('>', "Ошибка валидации");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('=', "Ошибка валидации");
    }

    private void expectedLTE()
    {
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('<', "Ошибка валидации");
        currentCharCaseInsensitiveEqCharacterAndDoNextChar('=', "Ошибка валидации");
    }
}
