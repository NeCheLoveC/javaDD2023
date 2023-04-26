import com.digdes.school.parser.ParserEngine;
import com.digdes.school.parser.ParsingResult;

public class Main {
    public static void main(String[] args) {
        ParserEngine parserEngine = new ParserEngine("   \n  select where 'test1' like '%d' and 'test2' = 3 Or 'test3' > 5.5");
        ParsingResult result = parserEngine.parse();
    }
}