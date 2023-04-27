import com.digdes.school.JavaSchoolStarter;
import com.digdes.school.Table;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Table starter = new JavaSchoolStarter();
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