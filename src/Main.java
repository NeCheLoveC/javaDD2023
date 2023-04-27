import com.digdes.school.JavaSchoolStarter;
import com.digdes.school.Table;
import com.digdes.school.parser.ParserEngine;
import com.digdes.school.parser.ParsingResult;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Table starter = new JavaSchoolStarter();
        /*
        table.execute("insert values 'id' = 0, 'name' = 'Victor'");
        table.execute("insert values 'id' = 1, 'name' = 'Anton'");
        table.execute("insert values 'id' = 2, 'name' = 'Egor'");
        table.execute("insert values 'id' = 3, 'name' = 'Michael'");
        table.execute("insert values 'id' = 4, 'name' = 'Alex'");
        table.execute("insert values 'id' = 5, 'name' = 'Danil'");
        table.execute("insert values 'id' = 6, 'name' = 'Rustam'");
        table.execute("insert values 'id' = 7, 'name' = 'Egor'");
        table.execute("insert values 'id' = 8, 'name' = 'Vic'");
        List<Map<String, Object>> result1 = table.execute("select");
        List<Map<String, Object>> result2 = table.execute("select where 'name' ilike 'v%'");
        List<Map<String, Object>> result3 = table.execute("delete   where  'name' = 'Anton'    ");
        List<Map<String, Object>> result4 = table.execute("select");
         */
        try
        {
            //Вставка строки в коллекцию
            List<Map<String,Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true");
            //Изменение значения которое выше записывали
            List<Map<String,Object>> result2 = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3");
            //Получение всех данных из коллекции (т.е. в данном примере вернется 1 запись)
            List<Map<String,Object>> result3 = starter.execute("SELECT");
            System.out.println();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}