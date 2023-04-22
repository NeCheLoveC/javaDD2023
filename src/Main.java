import com.digdes.school.parser.ParserEngine;
import com.digdes.school.parser.ParsingResult;

public class Main {
    public static void main(String[] args) {
        ParserEngine parserEngine = new ParserEngine("   \n  insert values  'test' = 123\n,\n'test2' = null");
        ParsingResult result = parserEngine.parse();
    }
}